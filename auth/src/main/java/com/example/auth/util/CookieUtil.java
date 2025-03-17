package com.example.auth.util;

import com.example.auth.security.JwtTokenType;
import com.example.auth.security.SecurityConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public void setJwtTokenToCookie(HttpServletResponse response, String token, JwtTokenType tokenType) {
        ResponseCookie responseCookie = ResponseCookie
                .from(tokenType == JwtTokenType.ACCESS_TOKEN
                        ? SecurityConstants.ACCESS_TOKEN_NAME
                        : SecurityConstants.REFRESH_TOKEN_NAME
                        ,token
                )
                .maxAge(tokenType == JwtTokenType.ACCESS_TOKEN
                        ? 60 * 60
                        : 60 * 60 * 24 * 7
                        )
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
