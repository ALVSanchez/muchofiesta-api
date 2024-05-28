package com.example.demo.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.challenge.Challenge;
import com.example.demo.user.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(summary = "Sign up a user", description = "Returns an authentication token if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success specified in the \"result\" enum"),
    })
    @PostMapping("/api/v1/noAuth/register")
    public ResponseEntity<RegistrationResult> register(@RequestBody RegistrationRequest request) {

        RegistrationResult result = authService.register(request,Role.ROLE_USER);
        if (result.getResult() == RegistrationResult.Result.EmailInUse) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/v1/admin/registerAdmin")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<RegistrationResult> registerAdmin(@RequestBody RegistrationRequest request) {

        RegistrationResult result = authService.register(request, Role.ROLE_ADMIN);
        if (result.getResult() == RegistrationResult.Result.EmailInUse) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Authenticate with login credentials", description = "Returns an authentication token if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            
            // TODO: Código de error si falla (Retrofit no deja recuperar el enum Result si el código es de error)
            //@ApiResponse(responseCode = "401", description = "Wrong credentials, specified by the \"result\" enum")
    })
    @PostMapping("/api/v1/noAuth/authenticate")
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
