package com.example.demo.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.challenge.Challenge.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    @Autowired
    private ChallengeRespository challengeRespository;

    public List<Challenge> getAllChallenges() {
        return challengeRespository.findAll();
    }

    public List<Challenge> getChallenges() {
        long challengeCount = challengeRespository.count();
        ArrayList<Challenge> remainingChallenges = new ArrayList<>(challengeRespository.findAll());
        ArrayList<Challenge> nonTimedChallenges = new ArrayList<>(new ArrayList<>(remainingChallenges)
            .stream()
            .filter(ch -> ch.getCategory() != Category.Timer)
            .toList());
        List<Challenge> pickedChallenges = new ArrayList<>();

        int timedCountdown = 0;
        int pickCount = 0;
        while(pickCount < challengeCount) {
            Challenge pickedChallenge;
            if(timedCountdown > 0 || pickCount == challengeCount - 1){
                if(nonTimedChallenges.size() == 0){
                    break;
                }
                pickedChallenge = nonTimedChallenges.get(new Random().nextInt(nonTimedChallenges.size()));
            } else {
                if(remainingChallenges.size() == 0) {
                    break;
                }
                pickedChallenge = remainingChallenges.get(new Random().nextInt(remainingChallenges.size()));
            }
            
            if(pickedChallenge.getCategory() == Category.Timer){
                if(timedCountdown > 0){
                    continue;
                }
                try {
                    timedCountdown = Integer.parseInt(pickedChallenge.getData().get("duration"));
                } catch (Exception e) {
                    continue;
                }
            }
            remainingChallenges.remove(pickedChallenge);
            nonTimedChallenges.remove(pickedChallenge);
            pickedChallenges.add(pickedChallenge);
            pickCount++;
            timedCountdown--;
        }

        return pickedChallenges;
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