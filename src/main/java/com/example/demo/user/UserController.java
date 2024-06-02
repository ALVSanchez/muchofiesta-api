package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.auth.AuthData;
import com.example.demo.auth.RegistrationRequest;
import com.example.demo.auth.RegistrationResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthData authData;

    @Operation(summary = "Post a profile picture", description = "Returns the image id if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success if the image id is not null"),
    })
    @PostMapping("/api/v1/user/postProfilePic")
    public ResponseEntity<Integer> postProfilePic(@RequestParam(value = "profilePic", required = false) MultipartFile profilePic) {

        Integer result = userService.postProfilePic(userService.getAuthUser(authData), profilePic);
        
        return ResponseEntity.ok(result);
    }

}
