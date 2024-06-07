package com.example.demo.history;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.AuthData;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/api/v1/user")
@RestController
@RequiredArgsConstructor
public class GameLogController {

    @Autowired
    private final GameLogService gameLogService;

    @Autowired
    private final UserService userService;

    @Autowired
    private AuthData authData;

    @GetMapping("/getHistory")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<GameLogResponse>> getHistory(@RequestParam Integer page, @RequestParam Integer pageSize) {
        User user = userService.getAuthUser(authData);
        List<GameLogResponse> logs = gameLogService.getHistory(user, page, pageSize);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/suggestedPlayers")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<String>> getSuggestedPlayers() {
        User user = userService.getAuthUser(authData);
        List<String> suggestedPlayers = gameLogService.getSuggestedPlayers(user);
        return ResponseEntity.ok(suggestedPlayers);
    }

    @PostMapping("/startGame")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> startGame(
            @RequestParam(value = "players") List<String> players,
            @RequestParam(value = "startTime") Long startTime,
            @RequestParam(value = "startPhoto", required = false) MultipartFile startPhoto) {
        Date startTimeDate = new Date(startTime);

        User user = userService.getAuthUser(authData);
        gameLogService.startGame(user, players, startTimeDate, Optional.ofNullable(startPhoto));
        return ResponseEntity.ok("Operation was successful.");
    }

    @PostMapping("/endGame")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> endGame(
            @RequestParam(value = "endTime") Long endTime,
            @RequestParam(value = "endPhoto", required = false) MultipartFile endPhoto) {

        Date endTimeDate = new Date(endTime);

        User user = userService.getAuthUser(authData);
        gameLogService.endGame(user, endTimeDate, Optional.ofNullable(endPhoto));
        return ResponseEntity.ok("Operation was successful.");
    }

}
