package org.norelaxgui.login;

public class LoginResponse {
  private String token;
  private boolean accountType;

  public LoginResponse(){}

  public String getToken() {
    return token;
  }

  public boolean isAccountType() {
    return accountType;
  }
}


