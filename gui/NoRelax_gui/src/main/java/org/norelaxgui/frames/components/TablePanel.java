package org.norelaxgui.frames.components;

import org.norelaxgui.api.RetrofitClient;
import org.norelaxgui.api.ApiService;
import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
  private JTable table;
  private DefaultTableModel tableModel;
  private ApiService apiService;
  private String token;

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
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return component;
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

    call.enqueue(new Callback<List<T>>() {
      @Override
      public void onResponse(Call<List<T>> call, Response<List<T>> response) {
        if (response.isSuccessful() && response.body() != null) {
          for (T item : response.body()) {
            if (item instanceof Order order) {
              tableModel.addRow(new Object[]{
                  order.getId(),
                  order.getDate(),
                  order.getUpdatedAt(),
                  order.getStatus(),
                  order.getFullPrice()
              });
            } else if (item instanceof Product product) {
              tableModel.addRow(new Object[]{
                  product.getProductName(),
                  product.getUnit(),
                  product.getPrice()
              });
            } else if (item instanceof Reservation reservation) {
              tableModel.addRow(new Object[]{
                  reservation.getId(),
                  reservation.getReservationDate(),
                  reservation.getTableNumber(),
                  reservation.isReserved()
              });
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
  }

  public void showOrders(){
    loadData(apiService.getOrders("Bearer " + this.token), new String[]{"ID", "Date", "Updated At", "Status", "Full Price"});
  }

  public void showProducts(){
    loadData(apiService.getProducts(), new String[]{"Name", "Unit", "Price"});
  }

  public void showReservations(){
    loadData(apiService.getReservations("Bearer " + this.token), new String[]{"ID", "Reservation Date", "Table Number", "Is Reserved"});
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
