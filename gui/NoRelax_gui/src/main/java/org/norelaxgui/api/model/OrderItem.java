package org.norelaxgui.api.model;

public class OrderItem {
  private String id;
  private String productName;
  private int quantity;
  private String orderId;

  public OrderItem(String productName, int quantity, String orderId) {
    this.orderId = orderId;
  }

  public String getId() {
    return id;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }
}
