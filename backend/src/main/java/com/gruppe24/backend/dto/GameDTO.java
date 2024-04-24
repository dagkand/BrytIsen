package com.gruppe24.backend.dto;

import com.gruppe24.backend.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Represents a Game-Data Transfer Object.
 * <p>
 * This class carries information from the json-object retrieved from the frontend.
 * </p>
 */
public class GameDTO {

  private Long ID;
  private String name;
  private String description;
  private String rules;
  private String emoji;
  private int players_min;
  private int players_max;
  private int duration_min;
  private int duration_max;

  private float rating;


  public Optional<Long> getID() {
    return Optional.of(ID);
  }

  public void setID(Long ID) {
    this.ID = ID;
  }

  private List<Category> categories;

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Optional<String> getRules() {
    return Optional.ofNullable(rules);
  }

  public void setRules(String rules) {
    this.rules = rules;
  }

  public Optional<String> getEmoji() {
    return Optional.ofNullable(emoji);
  }

  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }

  public Optional<Integer> getPlayers_max() {
    return Optional.of(players_max);
  }

  public void setPlayers_max(int players) {
    this.players_max = players;
  }

  public Optional<Integer> getPlayers_min() {
    return Optional.of(players_min);
  }

  public void setPlayers_min(int players) {
    this.players_min = players;
  }

  public Optional<Integer> getDuration_max() {
    return Optional.of(duration_max);
  }

  public void setDuration_max(int duration) {
    this.duration_max = duration;
  }

  public Optional<Integer> getDuration_min() {
    return Optional.of(duration_min);
  }

  public void setDuration_min(int duration) {
    this.duration_min = duration;
  }


  public Optional<List<Category>> getCategories() {
    return Optional.of(categories);
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    return "GameDTO{" +
            "ID=" + ID +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", rules='" + rules + '\'' +
            ", emoji='" + emoji + '\'' +
            ", players_min=" + players_min +
            ", players_max=" + players_max +
            ", duration_min=" + duration_min +
            ", duration_max=" + duration_max +
            ", categories=" + categories +
            '}';
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }
}
