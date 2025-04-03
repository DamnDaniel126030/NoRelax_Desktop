package org.norelaxgui.api.model;

public class Product {
  private int id;
  private String productName;
  private String unit;
  private double price;

  public Product(String productName, String unit, double price) {
    this.productName = productName;
    this.unit = unit;
    this.price = price;
  }

  public int getId() {
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

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public void setPrice(double price) {
    this.price = price;
  }


}
