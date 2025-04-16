package org.norelaxgui.api.model;

public class Product {
  private String id;
  private String productName;
  private String unit;
  private double price;

  public Product(String productName, String unit, double price) {
    this.productName = productName;
    this.unit = unit;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public String getProductName() {
    return productName;
  }

  public String getUnit() {
    return unit;
  }

  public double getPrice() {
    return price;
  }
}
