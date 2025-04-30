package com.jobsearchportal.controller;

import com.jobsearchportal.DTO.request.AuthRequest;
import com.jobsearchportal.DTO.response.AuthResponse;
import com.jobsearchportal.security.CustomUserDetailsService;
import com.jobsearchportal.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}

