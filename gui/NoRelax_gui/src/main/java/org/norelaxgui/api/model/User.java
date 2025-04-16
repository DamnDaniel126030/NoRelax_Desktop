package org.norelaxgui.api.model;

public class User {
  private String id;
  private String firstName;
  private String lastName;
  private String email;

  public User(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return this.firstName + " " + this.lastName;
  }
}
