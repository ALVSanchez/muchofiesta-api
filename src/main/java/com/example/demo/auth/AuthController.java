package com.example.demo.auth;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/noAuth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        
        RegistrationResult result = authService.register(request);
        if(result.getResult() == RegistrationResult.Result.EmailExists) {
            // TODO: Error response type + swagger
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(result.getResponse());
    }

    @GetMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest entity) {
        
        try {
            return ResponseEntity.ok(authService.authenticate(entity));
        } catch (BadCredentialsException e) {
            // TODO: Error response type + swagger
            return ResponseEntity.status(403).body(null);
        }
    }
    
}
