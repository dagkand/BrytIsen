package com.gruppe24.backend.repository;

import com.gruppe24.backend.idclass.MadeGameID;
import com.gruppe24.backend.relation.MadeGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MadeGameRepository extends JpaRepository<MadeGame, MadeGameID> {

  Optional<List<MadeGame>> findByUser_UserName(String name);

  Optional<MadeGame> findByGame_ID(Long gameID);

}
