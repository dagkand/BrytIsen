package com.gruppe24.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Represents a Category entity in the database.
 * <p>
 * This class is mapped to the Category table in the database and includes the name of the category.
 * </p>
 */
@Entity
public class Category {

  @Id
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Category [name=" + name + "]";
  }

}
