package com.group2.AutismAssesmentBackend.repository;

import com.group2.AutismAssesmentBackend.entities.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, Long> {
    @Query("SELECT e FROM UserDataEntity e WHERE e.Token = ?1")
    Optional<UserDataEntity> findByToken(String Token);
}
