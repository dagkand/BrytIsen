package com.gruppe24.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Represents a user entity in the database.
 * <p>
 * This class is mapped to the User table in the database and includes details about each user such as its name and email, and if it is admin.
 * </p>
 */
@Entity
public class User {

  private String email;

  @Id
  private String userName;

  private boolean admin;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  @Override
  public String toString() {
    return "User [userName=" + userName + ", email=" + email + ", admin=" + admin + "]";
  }


}
