package org.norelaxgui.frames.components;

import org.norelaxgui.api.RetrofitClient;
import org.norelaxgui.api.ApiService;
import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.OrderItem;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import org.norelaxgui.api.model.User;
import org.norelaxgui.view.ButtonEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TablePanel extends JPanel {
  private JTable table;
  private DefaultTableModel tableModel;
  private ApiService apiService;
  private String token;
  private List<String> productIds = new ArrayList<>();
  private List<String> orderIds = new ArrayList<>();
  private List<String> reservationIds = new ArrayList<>();

  public TablePanel(String token) {
    this.token = token;
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createEmptyBorder());

    apiService = RetrofitClient.getClient().create(ApiService.class);

    tableModel = new DefaultTableModel();

    table = new JTable(tableModel);
    table.setFillsViewportHeight(true);
    table.setRowHeight(30);
    table.setOpaque(false);
    table.setBorder(BorderFactory.createEmptyBorder());
    table.setGridColor(Color.WHITE);
    table.setFont(new Font("Ariel", Font.PLAIN, 16));
    table.setForeground(Color.WHITE);
    table.setSelectionBackground(Color.WHITE);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    showProducts();

    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected){
          component.setBackground(table.getSelectionBackground());
          component.setForeground(Color.BLACK);
        } else {
          component.setBackground(new Color(0, 0, 0, 0));
          component.setForeground(Color.WHITE);
        }
        if (component instanceof JLabel label){
          label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return component;
      }
    });

    table.getModel().addTableModelListener(e -> {
      if (e.getType() == TableModelEvent.UPDATE){
        int row = e.getFirstRow();
        int column = e.getColumn();
        if (row < 0 || row >= table.getRowCount() || column < 0 || column >= table.getColumnCount() || row >= productIds.size()){
          return;
        }
        String productId = productIds.get(row).toString();

        String name = table.getValueAt(row, 0).toString();
        String unit = table.getValueAt(row, 1).toString();
        double price = Double.parseDouble(table.getValueAt(row, 2).toString());
        Product updatedProduct = new Product(name, unit, price);

        apiService.updateProduct(("Bearer " + this.token), productId, updatedProduct)
            .enqueue(new Callback<>() {
              @Override
              public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                  System.out.println("Successfully updated product");
                } else {
                  System.out.println("Failed to update product");
                  System.out.println(response.code());
                  if (response.errorBody() != null) {
                    try {
                      System.out.println(response.errorBody().string());
                    } catch (IOException ex) {
                      throw new RuntimeException(ex);
                    }
                  }
                }
              }

              @Override
              public void onFailure(Call<Void> call, Throwable throwable) {
                System.out.println("Error updating product " + throwable.getMessage());
              }
            });
      }
    });



    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    add(scrollPane, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private <T> void loadData(Call<List<T>> call, String[] columns) {
    tableModel.setRowCount(0);
    tableModel.setColumnIdentifiers(columns);
    table.setModel(tableModel);
    String token = this.token;

    call.enqueue(new Callback<List<T>>() {
      @Override
      public void onResponse(Call<List<T>> call, Response<List<T>> response) {
        productIds.clear();
        orderIds.clear();
        reservationIds.clear();
        if (response.isSuccessful() && response.body() != null) {
          for (T item : response.body()) {
            if (item instanceof Order order && order.getStatus().equals("pending")) {
              DeleteButton deleteButton = new DeleteButton("Rendelések",
                  apiService.updateOrder("Bearer " + token, order.getId(), getBody(order)), TablePanel.this::showOrders);
              tableModel.addRow(new Object[]{
                  "Betöltés..",
                  "Betöltés..",
                  "Betöltés..",
                  order.getFullPrice(),
                  deleteButton
              });
              int currentRow = table.getRowCount() - 1;
              showUser(apiService.getUserById("Bearer " + token, order.getUserId()), username -> {
                tableModel.setValueAt(username, currentRow, 0);
              });
              showOrderItems(apiService.getOrderItems("Bearer " + token, order.getId()), orderItems -> {
                tableModel.setValueAt(orderItems, currentRow, 1);
              });
              showTableNumber(apiService.getReservationById("Bearer " + token, order.getUserId()), tableNumber -> {
                tableModel.setValueAt(tableNumber, currentRow, 2);
              });
              orderIds.add(order.getId());
            } else if (item instanceof Product product) {
              DeleteButton deleteButton = new DeleteButton("Itallap",
                  apiService.deleteProduct("Bearer " + token, product.getId()), TablePanel.this::showProducts);
              tableModel.addRow(new Object[]{
                  product.getProductName(),
                  product.getUnit(),
                  product.getPrice(),
                  deleteButton
              });
              productIds.add(product.getId());
            } else if (item instanceof Reservation reservation) {
              tableModel.addRow(new Object[]{
                  "Betöltés..",
                  reservation.getReservationDate(),
                  reservation.getTableNumber(),
                  reservation.getNumberOfSeats()
              });
              int currentRow = table.getRowCount() - 1;
              showUser(apiService.getUserById("Bearer " + token, reservation.getUserId()), username -> {
                tableModel.setValueAt(username, currentRow, 0);
              });
              reservationIds.add(reservation.getId());
            }
          }
        } else {
          System.out.println(response.code());
          if (response.errorBody() != null) {
            try {
              System.err.println("Hibaüzenet: " + response.errorBody().string());
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        }
        adjustColumnWidth();
      }

      @Override
      public void onFailure(Call<List<T>> call, Throwable throwable) {
        JOptionPane.showMessageDialog(TablePanel.this,
            "Error loading data: " + throwable.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    if (Arrays.asList(columns).contains("Törlés")){
      table.getColumn("Törlés").setCellRenderer(new TableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          return (Component) value;
        }
      });
      table.getColumn("Törlés").setCellEditor(new ButtonEditor());
    }
  }

  public void showOrders(){
    loadData(apiService.getOrders("Bearer " + this.token),
        new String[]{"Rendelő neve", "Termékek", "Asztalszám", "Teljes Ár", "Törlés"});
  }

  public void showProducts(){
    loadData(apiService.getProducts(),
        new String[]{"Név", "Mérték", "Ár", "Törlés"});
  }

  public void showReservations(){
    loadData(apiService.getReservations("Bearer " + this.token),
        new String[]{"Foglaló neve","Foglalás dátuma", "Asztalszám", "Székek száma"});
  }

  public void showUser(Call<User> call, Consumer<String> callback) {
    call.enqueue(new Callback<>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful() && response.body() != null) {
          User user = response.body();
          callback.accept(user.toString());
        }
        else {
          System.out.println(response.code());
          if (response.errorBody() != null) {
            try {
              System.err.println("Hibaüzenet: " + response.errorBody().string());
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable throwable) {
        JOptionPane.showMessageDialog(TablePanel.this,
            "Error loading data: " + throwable.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  public void showOrderItems(Call<List<OrderItem>> call, Consumer<String> callback) {
    call.enqueue(new Callback<List<OrderItem>>() {
      @Override
      public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
        if (response.isSuccessful() && response.body() != null) {
          Map<String, Integer> itemMap = new HashMap<>();
          for (OrderItem item : response.body()) {
            itemMap.put(item.getProductName(), itemMap.getOrDefault(item.getProductName(), 0) + item.getQuantity());
          }
          StringBuilder builder = new StringBuilder();
          for (Map.Entry<String, Integer> entry : itemMap.entrySet()) {
            builder.append(entry.getKey()).append(" x").append(entry.getValue()).append(", ");
          }
          if (builder.length() > 2) {
            builder.setLength(builder.length() - 2);
          }
          callback.accept(builder.toString().trim());
        } else {
          System.out.println(response.code());
          if (response.errorBody() != null) {
            try {
              System.err.println("Hibaüzenet: " + response.errorBody().string());
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        }
      }

      @Override
      public void onFailure(Call<List<OrderItem>> call, Throwable throwable) {
        JOptionPane.showMessageDialog(TablePanel.this,
            "Error loading data: " + throwable.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  public void showTableNumber(Call<List<Reservation>> call, Consumer<String> callback) {
    call.enqueue(new Callback<List<Reservation>>() {
      @Override
      public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
        if (response.isSuccessful() && response.body() != null) {
          List<Reservation> reservations = response.body();
          callback.accept(reservations.getFirst().getTableNumber());
        } else {
          System.out.println(response.code());
          if (response.errorBody() != null) {
            try {
              System.err.println("Hibaüzenet: " + response.errorBody().string());
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        }
      }

      @Override
      public void onFailure(Call<List<Reservation>> call, Throwable throwable) {
        JOptionPane.showMessageDialog(TablePanel.this,
            "Error loading data: " + throwable.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }

  public Order getBody(Order order){
    Order orderUpToDate = order;
    orderUpToDate.setStatus("completed");
    return orderUpToDate;
  }

  private void adjustColumnWidth(){
    for (int column = 0; column < table.getColumnCount(); column++){
      int maxWidth = 0;
      for (int row = 0; row < table.getRowCount(); row++){
        Object value = table.getValueAt(row, column);
        int cellWidth = table.getCellRenderer(row, column).getTableCellRendererComponent(table, value, false, false, row, column).getPreferredSize().width;
        maxWidth = Math.max(maxWidth, cellWidth);
      }
      table.getColumnModel().getColumn(column).setPreferredWidth(maxWidth + 10);
    }
  }
}
