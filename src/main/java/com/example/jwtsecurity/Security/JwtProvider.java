package com.example.jwtsecurity.Security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    public static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${voke.app.jwtSecret}")
    private String jwtSecret;

    @Value("${voke.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("role", userPrincipal.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JwtProperties.SECRET)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (ExpiredJwtException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtProperties.SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();

    }

}