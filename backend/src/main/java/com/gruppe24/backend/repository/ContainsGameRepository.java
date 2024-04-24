package com.gruppe24.backend.repository;

import com.gruppe24.backend.idclass.ContainsGameID;
import com.gruppe24.backend.relation.ContainsGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContainsGameRepository extends JpaRepository<ContainsGame, ContainsGameID> {

  Optional<List<ContainsGame>> findByGameListIDOrderByNumberInList(Long ID);

  Optional<ContainsGame> findByGameList_IDAndGame_ID(Long listID, Long gameID);

}
