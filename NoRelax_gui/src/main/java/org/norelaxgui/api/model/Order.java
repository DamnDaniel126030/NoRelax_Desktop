package org.norelaxgui.api.model;

public class Order {
  private String id;
  private double fullPrice;
  private String status;
  private String reservationId;
  private String userId;

  public Order(double fullPrice, String status, String reservationId, String userId) {
    this.fullPrice = fullPrice;
    this.reservationId = reservationId;
    this.userId = userId;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public double getFullPrice() {
    return fullPrice;
  }

  public String getReservationId() {
    return reservationId;
  }

  public String getUserId() {
    return userId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
