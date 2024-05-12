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

    public User getAuthUser(AuthData authResult) {
        Optional<User> user = userRepository.findByEmail(authResult.getEmail());
        if(user.isEmpty()){
            // No debería poder ocurrir
            //TODO: Manejar error
            throw new Error();
        }
        return user.get();
    }

    public Optional<User> getAuthUserOptional(AuthData authResult) {
        return userRepository.findByEmail(authResult.getEmail());
    }

}
