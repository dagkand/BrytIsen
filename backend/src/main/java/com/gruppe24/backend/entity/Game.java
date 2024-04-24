package com.gruppe24.backend.entity;

import jakarta.persistence.*;

/**
 * Represents a game entity in the database.
 * <p>
 * This class is mapped to the Game table in the database and includes details about each game such as its ID, name, rating and description.
 * </p>
 */
@Entity
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long ID;

  private String name;
  @Column(columnDefinition = "TEXT")
  private String description;
  @Column(columnDefinition = "TEXT")
  private String rules;
  private int players_min;
  private int players_max;
  private int duration_min;
  private int duration_max;
  private String emoji;
  private float rating;
  private int reviewCount;
  private int reportCount;

  public long getID() {
    return ID;
  }

  public void setID(long ID) {
    this.ID = ID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRules() {
    return rules;
  }

  public void setRules(String rules) {
    this.rules = rules;
  }

  public int getPlayers_min() {
    return players_min;
  }

  public void setPlayers_min(int players_min) {
    this.players_min = players_min;
  }

  public int getPlayers_max() {
    return players_max;
  }

  public void setPlayers_max(int players_max) {
    this.players_max = players_max;
  }

  public int getDuration_min() {
    return duration_min;
  }

  public void setDuration_min(int duration_min) {
    this.duration_min = duration_min;
  }

  public int getDuration_max() {
    return duration_max;
  }

  public void setDuration_max(int duration_max) {
    this.duration_max = duration_max;
  }

  public String getEmoji() {
    return emoji;
  }

  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public int getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(int reviewCount) {
    this.reviewCount = reviewCount;
  }

  public int getReportCount() {
    return reportCount;
  }

  public void setReportCount(int reportCount) {
    this.reportCount = reportCount;
  }

  @Override
  public String toString() {
    return "Game{" +
            "ID=" + ID +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", rules='" + rules + '\'' +
            ", players_min=" + players_min +
            ", players_max=" + players_max +
            ", duration_min=" + duration_min +
            ", duration_max=" + duration_max +
            ", emoji='" + emoji + '\'' +
            ", rating=" + rating +
            ", reviewCount=" + reviewCount +
            ", reportCount=" + reportCount +
            '}';
  }
}
