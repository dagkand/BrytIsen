package com.gruppe24.backend.repository;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.idclass.ReviewID;
import com.gruppe24.backend.relation.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewID> {

  Optional<List<Review>> findByUser_UserName(String name);

  Optional<List<Review>> findByGame_ID(Long gameID);

  Optional<Review> findByUserAndGame(User user, Game game);

  Optional<Review> findByUser_UserNameAndGame_ID(String name, Long gameID);

}
