package com.example.demo.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthenticationResult.Result;
import com.example.demo.config.JwtService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public RegistrationResult register(RegistrationRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return RegistrationResult.builder().result(RegistrationResult.Result.EmailInUse).build();
        }

        Role userRole;
        // TODO: load secret properly or scrap this idea
        if ("PAgeo4X_7sznVDWISu5CMg".equals(request.getAdminSecret())) {
            userRole = Role.ROLE_ADMIN;
        } else {
            userRole = Role.ROLE_USER;
        }

        // TODO: check for valid email / user / password
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new RegistrationResult(RegistrationResult.Result.Ok, jwtToken);
    }

    public AuthenticationResult authenticate(AuthenticationRequest request) throws BadCredentialsException {

        Optional<User> userOptional = repository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                return new AuthenticationResult(AuthenticationResult.Result.WrongPassword, null);
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResult.builder()
                    .result(Result.Ok)
                    .token(jwtToken)
                    .build();

        } else {
            return new AuthenticationResult(AuthenticationResult.Result.WrongEmail, null);
        }
    }
}
