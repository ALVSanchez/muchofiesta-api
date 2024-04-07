package com.example.demo.auth;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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


    @Operation(summary = "Sign up a user", description = "Returns an authentication token if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials, specified by the \"result\" enum")
    })
    @PostMapping("/register")
    public ResponseEntity<RegistrationResult> register(@RequestBody RegisterRequest request) {

        RegistrationResult result = authService.register(request);
        if (result.getResult() == RegistrationResult.Result.EmailExists) {
            return ResponseEntity.status(403).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Authenticate with login credentials", description = "Returns an authentication token if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            
            // TODO: Código de error si falla (Retrofit no deja recuperar el enum Result si el código es de error)
            //@ApiResponse(responseCode = "401", description = "Wrong credentials, specified by the \"result\" enum")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResult> authenticate(@RequestBody AuthenticationRequest request) throws BadCredentialsException {

        AuthenticationResult result = authService.authenticate(request);
        if(result.getResult() == AuthenticationResult.Result.Ok){
            return ResponseEntity.ok(result);    
        } else {
            // TODO: Código de error si falla (Retrofit no deja recuperar el enum Result si el código es de error)
            return ResponseEntity.status(200).body(result);
        }
    }

}
