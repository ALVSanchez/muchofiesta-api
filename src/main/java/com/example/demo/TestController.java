package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.AuthData;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/api/v1/user/test")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TestController {
    
    @Autowired
    UserService userService;

    @Autowired
    private final AuthData authResult;

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername() {
        Optional<User> authUser = userService.getAuthUser(authResult);
        return ResponseEntity.ok("\"" + authUser.get().getName() + "\"");
    }
    
}
