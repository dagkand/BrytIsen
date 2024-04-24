package com.gruppe24.backend.repository;

import com.gruppe24.backend.entity.Game;
import com.gruppe24.backend.idclass.HasCategoryID;
import com.gruppe24.backend.relation.HasCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HasCategoryRepository extends JpaRepository<HasCategory, HasCategoryID> {

  Optional<List<HasCategory>> findByGame_ID(Long gameID);

  Optional<List<HasCategory>> findByCategory_Name(String categoryName);

}