package org.norelaxgui.api.model;

import java.time.LocalDateTime;

public class Reservation {
  private int id;
  private boolean isReserved;
  private int tableNumber;
  private String reservationDate;

  public Reservation(boolean reserved, int tableNumber, String reservationDate) {
    this.isReserved = reserved;
    this.tableNumber = tableNumber;
    this.reservationDate = reservationDate;
  }

  public int getId() {
    return id;
  }

  public boolean isReserved() {
    return isReserved;
  }

  public int getTableNumber() {
    return tableNumber;
  }

  public String getReservationDate() {
    return reservationDate;
  }

  public void setReserved(boolean reserved) {
    isReserved = reserved;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public void setReservationDate(String reservationDate) {
    this.reservationDate = reservationDate;
  }
}
