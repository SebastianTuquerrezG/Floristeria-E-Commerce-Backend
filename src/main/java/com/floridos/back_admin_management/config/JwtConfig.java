package com.floridos.back_admin_management.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Valores de configuración JWT leídos desde application.properties.
 * JwtService los usa directamente con @Value, así que este bean
 * es solo para quien necesite acceder a ellos desde otros componentes.
 */
@Component
@Getter
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:3600}")
    private long expirationSeconds;

    @Value("${jwt.refresh-expiration:604800}")
    private long refreshExpirationSeconds;

    public String getHeader()      { return "Authorization"; }
    public String getTokenPrefix() { return "Bearer "; }
}
