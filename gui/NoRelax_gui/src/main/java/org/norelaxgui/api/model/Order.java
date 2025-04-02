package org.norelaxgui.api.model;

public class Order {
  private int id;
  private String date;
  private String updatedAt;
  private String status;
  private double fullPrice;

  public Order(String date, String updatedAt, String status, double fullPrice) {
    this.date = date;
    this.updatedAt = updatedAt;
    this.status = status;
    this.fullPrice = fullPrice;
  }

  public int getId() {
    return id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public double getFullPrice() {
    return fullPrice;
  }

  public void setFullPrice(double fullPrice) {
    this.fullPrice = fullPrice;
  }
}
