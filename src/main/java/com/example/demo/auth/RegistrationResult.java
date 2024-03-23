package com.example.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResult {
    enum Result {
        Success,
        EmailExists
    }

    private Result result;

    private AuthenticationResponse response;
}
