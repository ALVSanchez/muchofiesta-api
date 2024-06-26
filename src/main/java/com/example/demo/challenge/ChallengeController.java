package com.example.demo.challenge;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

//@RequestMapping("/api/v1/noAuth")
@RestController
@RequiredArgsConstructor
public class ChallengeController {
    @Autowired
    private final ChallengeService challengeService;

    @GetMapping("/api/v1/noAuth/getChallenges")
    public ResponseEntity<List<Challenge>> getChallenges() {
        List<Challenge> challenges = challengeService.getChallenges();
        return ResponseEntity.ok(challenges);
    }

    @GetMapping("/api/v1/noAuth/getAllChallenges")
    public ResponseEntity<List<Challenge>> getAllChallenges() {
        List<Challenge> challenges = challengeService.getAllChallenges();
        return ResponseEntity.ok(challenges);
    }     

    @PostMapping("/api/v1/admin/postChallenge")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Optional<Challenge>> postChallenge(@RequestBody Challenge ch) {
        Optional<Challenge> optCh = challengeService.postChallenge(ch);
        if (optCh.isPresent()) {
            return ResponseEntity.ok(optCh);
        } else {
            return ResponseEntity.badRequest().body(optCh);
        }
    }

    @PostMapping("/api/v1/admin/postChallenges")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> postChallenge(@RequestBody List<Challenge> ch) {
        boolean success = challengeService.postChallenges(ch);
        if (success) {
            return ResponseEntity.ok("Challenges saved succesfully");
        } else {
            return ResponseEntity.badRequest().body("An error has occured");
        }
    }

    @PostMapping("/api/v1/admin/initChallenges")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> initChallenges(@RequestBody List<Challenge> ch) {
        challengeService.initChallenges(ch);
        return ResponseEntity.ok("Operation successful");
    }

    @PostMapping("/api/v1/admin/removeChallenge")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> removeChallenge(@RequestBody Integer id) {
        challengeService.removeChallenge(id);

        return ResponseEntity.ok("Operation successful");
    }
}
