package com.gruppe24.backend.repository;

import com.gruppe24.backend.idclass.HasReportedID;
import com.gruppe24.backend.relation.HasReported;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HasReportedRepository extends JpaRepository<HasReported, HasReportedID> {

  Optional<HasReported> findByUser_UserNameAndGame_ID(String username, Long id);

}
