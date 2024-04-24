package com.gruppe24.backend.service;

import com.gruppe24.backend.dto.ReviewDTO;
import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.exception.ErrorCreatingRelationException;
import com.gruppe24.backend.exception.GameNotFoundException;
import com.gruppe24.backend.exception.InvalidDtoException;
import com.gruppe24.backend.exception.ReviewNotFoundException;
import com.gruppe24.backend.relation.Review;
import com.gruppe24.backend.repository.GameRepository;
import com.gruppe24.backend.repository.ReviewRepository;
import com.gruppe24.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

  private final GameRepository gameRepository;
  private final ReviewRepository reviewRepository;

  private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

  public ReviewService(GameRepository gameRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
    this.gameRepository = gameRepository;
    this.reviewRepository = reviewRepository;
  }


  /**
   * Tries to create a review based on given {@link ReviewDTO}.
   * Also updates the games rating.
   *
   * @param authenticatedUser the authenticated user that tires to create the review
   * @param id                id of the game the review is about
   * @param reviewDTO         data transfer object with information about the review
   */
  @Transactional
  public void createReview(User authenticatedUser, Long id, ReviewDTO reviewDTO) {
    if (gameRepository.findByID(id).isEmpty()) {
      throw new GameNotFoundException();
    }
    Game game = gameRepository.findByID(id).get();

    if (reviewRepository.findByUserAndGame(authenticatedUser, game).isPresent()) {
      throw new ErrorCreatingRelationException("The user already has a review on this game");
    }

    Review review = new Review();
    review.setUser(authenticatedUser);

    review.setGame(game);

    List<String> missingFields = new ArrayList<>();
    if (reviewDTO.getDescription().isEmpty()) {
      missingFields.add("description");
    }
    if (reviewDTO.getStars().isEmpty()) {
      missingFields.add("stars");
    }

    if (!missingFields.isEmpty()) {
      String errorMessage = "Missing required fields: " + String.join(", ", missingFields);
      throw new InvalidDtoException(errorMessage);
    }

    review.setDescription(reviewDTO.getDescription().get());
    review.setStars(reviewDTO.getStars().get());
    review.setCreatedAt(LocalDateTime.now());

    float currentTotalRating = game.getRating() * game.getReviewCount();
    game.setReviewCount(game.getReviewCount() + 1);
    float newRating = (currentTotalRating + review.getStars()) / game.getReviewCount();
    game.setRating(newRating);

    gameRepository.save(game);
    reviewRepository.save(review);
  }

  /**
   * Tries to delete the review created by given {@link User} about given {@link Game}.
   * It also updates the rating of the game.
   *
   * @param user   owner of the review
   * @param gameID id of game review is about
   * @throws GameNotFoundException   if it does not find the game with the given id.
   * @throws ReviewNotFoundException if it does not find the review with the given game and user
   */
  @Transactional
  public void deleteReview(User user, Long gameID) {
    Game game = gameRepository.findByID(gameID).orElseThrow(GameNotFoundException::new);

    Review review = reviewRepository.findByUserAndGame(user, game).orElseThrow(ReviewNotFoundException::new);
    float currentTotalRating = game.getRating() * game.getReviewCount();
    game.setReviewCount(game.getReviewCount() - 1);
    if (game.getReviewCount() != 0) {
      float newRating = (currentTotalRating - review.getStars()) / game.getReviewCount();
      game.setRating(newRating);
    } else {
      game.setRating(0);
    }

    gameRepository.save(game);
    reviewRepository.delete(review);
  }

  @Transactional
  public void updateReview(User user, Long gameID, ReviewDTO reviewDTO) {
    Game game = gameRepository.findByID(gameID).orElseThrow(GameNotFoundException::new);
    Review review = reviewRepository.findByUser_UserNameAndGame_ID(user.getUserName(), gameID).orElseThrow(ReviewNotFoundException::new);

    float oldRating = game.getRating() * game.getReviewCount() - review.getStars();

    reviewDTO.getDescription().ifPresent(review::setDescription);
    reviewDTO.getStars().ifPresent(review::setStars);

    float newRating = (oldRating + review.getStars()) / game.getReviewCount();
    game.setRating(newRating);

    gameRepository.save(game);
    reviewRepository.save(review);
  }

  @Transactional
  public List<Review> getUsersReviews(String username) {
    return reviewRepository.findByUser_UserName(username).orElse(List.of());
  }

  @Transactional
  public List<Review> getGamesReviews(Long ID) {
    return reviewRepository.findByGame_ID(ID).orElseThrow(ReviewNotFoundException::new);
  }

  public Review getReview(User user, Long id) {
    return reviewRepository.findByUser_UserNameAndGame_ID(user.getUserName(), id)
            .orElseThrow(ReviewNotFoundException::new);
  }
}
