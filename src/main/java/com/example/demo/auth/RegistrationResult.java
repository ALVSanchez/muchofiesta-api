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
        Ok,
        EmailInUse,
        InvalidData
    }

    private Result result;
    private String userName;
    private Integer profilePicId;
    private String token;
}
