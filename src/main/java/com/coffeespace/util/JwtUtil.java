package com.coffeespace.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    private Key signingKey;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalStateException("JWT secret key must be at least 32 characters long");
        }
        signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        log.info("✅ JWT signing key initialized. Expiration set to {} ms", jwtExpiration);
    }

    // ✅ Generate token with both userId & phoneNumber
    public String generateToken(String userId, String phoneNumber) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("phoneNumber", phoneNumber);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(phoneNumber) // still keep subject as phoneNumber
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("🎯 Generated JWT for userId={} phoneNumber={} expiresAt={}", userId, phoneNumber,
                new Date(System.currentTimeMillis() + jwtExpiration));

        return token;
    }

    // ✅ Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Extract phone number (subject)
    public String extractPhoneNumber(String token) {
        String phone = extractClaim(token, Claims::getSubject);
        log.debug("📞 Extracted phoneNumber={} from JWT", phone);
        return phone;
    }

    // ✅ Extract userId
    public String extractUserId(String token) {
        String uid = extractClaim(token, claims -> claims.get("userId", String.class));
        log.debug("🆔 Extracted userId={} from JWT", uid);
        return uid;
    }

    // ✅ Extract expiration
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // ✅ Token validation
    public boolean validateToken(String token, String phoneNumber) {
        boolean valid = extractPhoneNumber(token).equals(phoneNumber) && !isTokenExpired(token);
        log.info("🔍 Validating token for phoneNumber={} => {}", phoneNumber, valid ? "VALID" : "INVALID");
        return valid;
    }

    private boolean isTokenExpired(String token) {
        boolean expired = extractExpiration(token).before(new Date());
        if (expired) {
            log.warn("⏳ Token expired at {}", extractExpiration(token));
        }
        return expired;
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("❌ Failed to parse JWT: {}", e.getMessage());
            throw e;
        }
    }
}
