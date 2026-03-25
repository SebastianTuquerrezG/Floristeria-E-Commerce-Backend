package com.floridos.back_admin_management.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:3600}")
    private long expiration; // en segundos

    public String getHeader() {
        return "Authorization";
    }

    public String getTokenPrefix() {
        return "Bearer ";
    }
}
