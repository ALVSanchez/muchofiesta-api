package com.example.demo.auth;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer",
    bearerFormat = "JWT", 
    type = SecuritySchemeType.HTTP, 
    in = SecuritySchemeIn.HEADER
)
public class BearerAuth {
    
}
