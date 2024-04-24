package com.gruppe24.backend.repository;

import com.gruppe24.backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

  Optional<Game> findByID(Long ID);

}
