package com.example.demo.challenge;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    @Autowired
    private ChallengeRespository challengeRespository;

    public List<Challenge> getChallenges() {
        return challengeRespository.findAll();
    }

    public Optional<Challenge> postChallenge(Challenge ch) {
        return Optional.ofNullable(ch).map(optCh -> challengeRespository.save(optCh));
    }

    public void initChallenges(@NonNull List<Challenge> challenges) {
        challengeRespository.deleteAll();
        for(Challenge challenge: challenges) {
            challenge.setId(null);
            challengeRespository.saveAndFlush(challenge);
        }
    }

    // TODO: Check for null with custom error
    public void removeChallenge(@NonNull Integer id) {
        challengeRespository.deleteById(id);
    }

}