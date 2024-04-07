package com.example.demo.history;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/getHistory")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<GameLog>> getHistory(@RequestParam Integer page, @RequestParam Integer pageSize) {
        List<GameLog> logs = gameLogService.getHistory(page, pageSize);
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/postLog")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> postLog(@RequestParam("image") MultipartFile initialPhoto,
            @RequestParam("image") MultipartFile finalPhoto) {
        return ResponseEntity.ok("Operation was successful.");
    }

}
