package com.cc.workflow.services;

import com.cc.workflow.security.JWTUser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {
    @Value("${jwt.expire.hours}")
    private Long expireHours;

    @Value("${jwt.token.secret}")
    private String secret;

    private String encodedSecret;

    @PostConstruct
    protected void init() {
        this.encodedSecret = generateEncodedSecret(this.secret);
    }

    protected String generateEncodedSecret(String plainSecret) {
        if (StringUtils.isEmpty(plainSecret)) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty.");
        }
        return Base64
                .getEncoder()
                .encodeToString(this.secret.getBytes());
    }

    protected Date getExpirationTime() {
        Date now = new Date();
        Long expireInMilis = TimeUnit.HOURS.toMillis(expireHours);
        return new Date(expireInMilis + now.getTime());
    }

    protected JWTUser getUser(String encodedSecret, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody();
        String userName = claims.getSubject();
        JWTUser securityUser = new JWTUser();
        securityUser.setId(userName);
        return securityUser;
    }

    public JWTUser getUser(String token) {
        return getUser(this.encodedSecret, token);
    }

    protected String getToken(String encodedSecret, JWTUser JWTUser) {
        Date now = new Date();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(JWTUser.getId())
                .setIssuedAt(now)
                .setExpiration(getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    public String getToken(JWTUser JWTUser) {
        return getToken(this.encodedSecret, JWTUser);
    }
}