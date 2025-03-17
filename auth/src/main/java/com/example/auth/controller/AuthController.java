package com.example.auth.controller;

import com.example.auth.dto.AuthResponseDto;
import com.example.auth.dto.LoginDto;
import com.example.auth.dto.RegisterDto;
import com.example.auth.dto.UserResponseDto;
import com.example.auth.model.UserEntity;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtProvider;
import com.example.auth.security.JwtTokenType;
import com.example.auth.security.SecurityConstants;
import com.example.auth.service.UserService;
import com.example.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    public AuthController(UserRepository userRepository,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          CookieUtil cookieUtil
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            UserEntity user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow();

            String accessToken = jwtProvider.generateToken(user, JwtTokenType.ACCESS_TOKEN);
            String refreshToken = jwtProvider.generateToken(user, JwtTokenType.REFRESH_TOKEN);

            cookieUtil.setJwtTokenToCookie(response, accessToken, JwtTokenType.ACCESS_TOKEN);
            cookieUtil.setJwtTokenToCookie(response, refreshToken, JwtTokenType.REFRESH_TOKEN);

            return ResponseEntity.ok(new AuthResponseDto(accessToken, refreshToken, "User logged in successfully"));
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponseDto(null, null, "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterDto registerDto, HttpServletResponse response) {
        if (userRepository.existsUserByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(new AuthResponseDto(null, null, "Username already taken"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        String accessToken = jwtProvider.generateToken(user, JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtProvider.generateToken(user, JwtTokenType.REFRESH_TOKEN);

        cookieUtil.setJwtTokenToCookie(response, accessToken, JwtTokenType.ACCESS_TOKEN);
        cookieUtil.setJwtTokenToCookie(response, refreshToken, JwtTokenType.REFRESH_TOKEN);

        return ResponseEntity.ok(new AuthResponseDto(accessToken, refreshToken, "User registered successfully"));
    }

    @PostMapping("logout")
    public ResponseEntity<AuthResponseDto> logout(HttpServletResponse response) {
        cookieUtil.deleteCookie(response, SecurityConstants.ACCESS_TOKEN_NAME);
        cookieUtil.deleteCookie(response, SecurityConstants.REFRESH_TOKEN_NAME);
        return ResponseEntity.ok(new AuthResponseDto(null, null, "User logged out successfully"));
    }

    @GetMapping("me")
    public ResponseEntity<UserResponseDto> getMe(@AuthenticationPrincipal User user) {
        UserEntity currentUser =  userService.findByUsername(user.getUsername());
        return ResponseEntity.ok(UserResponseDto.toDto(currentUser));
    }
}
