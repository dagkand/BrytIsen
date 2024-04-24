package com.gruppe24.backend.repository;

import com.gruppe24.backend.entity.GameList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GameListRepository extends JpaRepository<GameList, Long> {

  Optional<GameList> findByID(Long ID);

}
