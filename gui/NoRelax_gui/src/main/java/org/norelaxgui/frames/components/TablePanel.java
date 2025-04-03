package org.norelaxgui.frames.components;

import org.norelaxgui.RetrofitClient;
import org.norelaxgui.api.ApiService;
import org.norelaxgui.api.model.Order;
import org.norelaxgui.api.model.Product;
import org.norelaxgui.api.model.Reservation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
  private JTable table;
  private DefaultTableModel tableModel;
  private ApiService apiService;

  public TablePanel() {
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
                  product.getId(),
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
    loadData(apiService.getOrders(), new String[]{"ID", "Date", "Updated At", "Status", "Full Price"});
  }

  public void showProducts(){
    loadData(apiService.getProducts("drinks"), new String[]{"ID", "Name", "Unit", "Price"});
  }

  public void showReservations(){
    loadData(apiService.getReservations(), new String[]{"ID", "Reservation Date", "Table Number", "Is Reserved"});
  }
}
