package com.alysrazor.library.service;

import com.alysrazor.library.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private SecretKey key;

    public String generateToken(final User user) {
        return buildToken(user ,jwtExpiration);
    }

    public String generateRefreshToken(final User user) {
        return buildToken(user, refreshExpiration);
    }


    private String buildToken(final User user, final long expiration) {
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getUsername()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  + expiration))
                .signWith(getSignKey(), Jwts.SIG.HS512)
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String refreshToken) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

        return jwtToken.getSubject();
    }

    public boolean isValidToken(String refreshToken, User user) {
        final String username = extractUsername(refreshToken);
        return (username.equals(user.getEmail())) && !isTokenExpired(refreshToken);
    }

    private boolean isTokenExpired(String refreshToken) {
        return extractExpiration(refreshToken).before(new Date());
    }

    private Date extractExpiration(String refreshToken) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
        return jwtToken.getExpiration();
    }
}
