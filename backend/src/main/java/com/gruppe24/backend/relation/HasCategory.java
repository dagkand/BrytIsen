package com.gruppe24.backend.relation;

import com.gruppe24.backend.entity.Category;
import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.idclass.HasCategoryID;
import jakarta.persistence.*;

/**
 * Represents a HasCategory relationship in the database.
 * <p>
 * This class contains the ID of the game and the name of the category it has.
 * </p>
 */
@Entity
@IdClass(HasCategoryID.class)
public class HasCategory {

    @Id
    @ManyToOne
    @JoinColumn(name = "GameID")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "CategoryName")
    private Category category;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "HasCategory{" +
                "game=" + game +
                ", category=" + category +
                '}';
    }
}
