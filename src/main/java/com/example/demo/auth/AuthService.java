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

    public static final String PSSWD_REGEX = "^[\\S]{8,24}$";

    public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";

    public static final String USERN_REGEX = "^[A-Za-z0-9_]{2,16}$";

    public RegistrationResult register(RegistrationRequest request, Role userRole) {

        if (request.getName() == null || !request.getName().matches(USERN_REGEX)) {
            return RegistrationResult.builder().result(RegistrationResult.Result.InvalidData).build();
        }
        else if (request.getEmail() == null || !request.getEmail().matches(EMAIL_REGEX)) {
            return RegistrationResult.builder().result(RegistrationResult.Result.InvalidData).build();
        } else if (request.getPassword() == null || !request.getPassword().matches(PSSWD_REGEX)) {
            return RegistrationResult.builder().result(RegistrationResult.Result.InvalidData).build();
        }
        

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return RegistrationResult.builder().result(RegistrationResult.Result.EmailInUse).build();
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
