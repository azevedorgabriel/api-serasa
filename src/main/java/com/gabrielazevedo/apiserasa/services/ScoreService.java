package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.repositories.PersonRepository;
import com.gabrielazevedo.apiserasa.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    @Autowired
    ScoreRepository scoreRepository;
    public String getScoreDescription(Integer score) {
        return scoreRepository.getScoreDescription(score);
    }
}
