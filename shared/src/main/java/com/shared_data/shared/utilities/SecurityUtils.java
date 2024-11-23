package com.shared_data.shared.utilities;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtils {

    @Value("${secret.key}")
    private static String jwtSecret;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String obtenerAliasDeJWT(String token){
        log.warn("inicia obtenerAliasDeJWT(String token)");
        Claims  claims = Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public static Boolean validarToken(String token) {
        log.warn("inicia validarToken(String token)");
        log.warn("-0-0-0-0-0 token del validar {}", token);
        try {
            Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("falla validarToken(String token)");
            return false;
        }
    }

    public static String encodePass(String password){
        return encoder.encode(password);
    }
}
