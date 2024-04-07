package com.example.demo.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    @Autowired
    private final UserRepository userRepository;

    public Optional<User> getAuthUser(AuthData authResult) {
        return userRepository.findByEmail(authResult.getEmail());
    }

}
