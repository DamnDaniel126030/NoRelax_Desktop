package org.norelaxgui.api.model;

public class Reservation {
  private String id;
  private String reservationDate;
  private String tableNumber;
  private String seats;
  private boolean isReserved;
  private String userId;

  public Reservation(boolean reserved, String tableNumber, String reservationDate, String userId, String seats) {
    this.isReserved = reserved;
    this.tableNumber = tableNumber;
    this.reservationDate = reservationDate;
    this.userId = userId;
    this.seats = seats;
  }

  public String getId() {
    return id;
  }

  public boolean isReserved() {
    return isReserved;
  }

  public String getTableNumber() {
    return tableNumber;
  }

  public String getReservationDate() {
    return reservationDate;
  }

  public String getUserId() {
    return userId;
  }

  public String getNumberOfSeats() {
    return seats;
  }
}
