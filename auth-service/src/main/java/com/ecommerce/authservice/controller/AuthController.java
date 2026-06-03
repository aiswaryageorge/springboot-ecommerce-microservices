package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.dto.AuthRequest;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.RefreshRequest;
import com.ecommerce.authservice.entity.AppUser;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody AppUser user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);

        return "User Registered";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        AppUser user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        if(passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            String accessToken  =  jwtService.generateToken(
                    user.getUsername(),
                    user.getRole()
            );
            String refreshToken =
                    jwtService.generateRefreshToken(
                            user.getUsername(),
                            user.getRole()
                    );

            return new AuthResponse(
                    accessToken,
                    refreshToken
            );
        }

        throw new RuntimeException(
                "Invalid Credentials"
        );
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshRequest request
    ) {
        String username =
                jwtService.extractUsername(
                        request.getRefreshToken()
                );
        AppUser user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow();
        String newAccessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole()
                );
        return new AuthResponse(
                newAccessToken,
                request.getRefreshToken()
        );
    }
}
