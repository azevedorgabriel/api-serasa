package com.gabrielazevedo.apiserasa.repositories;

import com.gabrielazevedo.apiserasa.models.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<ScoreModel, Long> {
    @Query("SELECT s.description FROM ScoreModel s WHERE :score >= s.min AND :score <= s.max")
    String getScoreDescription(Integer score);
}
