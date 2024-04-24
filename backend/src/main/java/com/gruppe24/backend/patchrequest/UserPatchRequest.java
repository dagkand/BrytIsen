package com.gruppe24.backend.patchrequest;

import java.util.Optional;

public class UserPatchRequest {

  private String username;

  private String email;


  public Optional<String> getUserName() {
    return Optional.ofNullable(username);
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
