package com.example.demo.history;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.AuthData;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<List<GameLog>> getHistory(@RequestParam Integer page, @RequestParam Integer pageSize) {
        User user = userService.getAuthUser(authData);
        List<GameLog> logs = gameLogService.getHistory(user, page, pageSize);
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/postGame")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> postLog(@RequestBody PostGameBody body,
            @RequestParam(value = "initialPhoto", required = false) Optional<MultipartFile> initialPhoto,
            @RequestParam(value = "finalPhoto", required = false) Optional<MultipartFile> finalPhoto) {
                System.out.println(initialPhoto == null);
        User user = userService.getAuthUser(authData);
        gameLogService.postGame(user, body, initialPhoto, finalPhoto);
        return ResponseEntity.ok("Operation was successful.");
    }

}
