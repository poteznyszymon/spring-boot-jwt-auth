package com.example.auth.controller;

import com.example.auth.dto.LoginDto;
import com.example.auth.dto.RegisterDto;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtProvider;
import com.example.auth.security.SecurityConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        ResponseCookie jwtCookie = ResponseCookie.from(SecurityConstants.ACCESS_TOKEN_NAME, token).maxAge(60 * 60).path("/").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto, HttpServletResponse response) {
        if (userRepository.existsUserByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), registerDto.getPassword());
        String token = jwtProvider.generateToken(authentication);
        ResponseCookie jwtCookie = ResponseCookie.from(SecurityConstants.ACCESS_TOKEN_NAME, token).maxAge(60 * 60).path("/").build();
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

}
