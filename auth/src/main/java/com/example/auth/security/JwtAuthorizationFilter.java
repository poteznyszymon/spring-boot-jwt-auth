package com.example.auth.security;

import com.example.auth.util.CookieUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final CookieUtil cookieUtil;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService, CookieUtil cookieUtil) {
        this.jwtProvider = jwtProvider;
        this.customUserDetailsService = customUserDetailsService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getJwtFromCookie(request, JwtTokenType.ACCESS_TOKEN);
        String refreshToken = getJwtFromCookie(request, JwtTokenType.REFRESH_TOKEN);

        if (accessToken != null && validateAndAuthenticate(accessToken, request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (refreshToken != null && accessToken == null) {
            String username = jwtProvider.getUsernameFromToken(refreshToken);
            if (username != null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if (jwtProvider.validateToken(refreshToken, userDetails)) {
                    String newAccessToken = jwtProvider.generateToken(userDetails.getUsername(), JwtTokenType.ACCESS_TOKEN);
                    cookieUtil.setJwtTokenToCookie(response, newAccessToken, JwtTokenType.ACCESS_TOKEN);
                    validateAndAuthenticate(newAccessToken, request);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateAndAuthenticate(String token, HttpServletRequest request) {
        String username = jwtProvider.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if (jwtProvider.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return true;
            }
        }
        return false;
    }

    private String getJwtFromCookie(HttpServletRequest request, JwtTokenType jwtTokenType) {
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (jwtTokenType == JwtTokenType.ACCESS_TOKEN ?
                        SecurityConstants.ACCESS_TOKEN_NAME.equals(cookie.getName())
                        : SecurityConstants.REFRESH_TOKEN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
