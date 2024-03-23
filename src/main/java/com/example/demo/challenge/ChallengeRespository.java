package com.example.demo.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRespository extends JpaRepository<Challenge, Integer>{
    
}
