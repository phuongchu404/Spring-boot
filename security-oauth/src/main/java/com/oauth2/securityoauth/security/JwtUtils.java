package com.oauth2.securityoauth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.util.Date;

@Component
@Getter
@Slf4j
public class JwtUtils {
    @Value("${app.security.jwt-secret}")
    private String jwtSecret;

    @Value("${app.security.jwt-expiration}")
    private long jwtExpiration;

    public String createToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.ES256, key())
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key()).parse(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid Jwt signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid Jwt token: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Jwt token is not supported: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Jwt token is expired: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Jwt claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token){
        Claims claims = Jwts.parser().setSigningKey(key()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
