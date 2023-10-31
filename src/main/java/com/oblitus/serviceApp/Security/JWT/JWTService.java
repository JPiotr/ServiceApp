package com.oblitus.serviceApp.Security.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${application.sec.jwt.secret-key}")
    private String KEY = "88cd2108b5347d973cf39cdf9053d7dd42704876d8c9a9bd8e2d168259d3ddf7";
    @Value("${application.sec.jwt.expiration}")
    private long EXPIRATION = 86400000;
    @Value("${application.sec.jwt.refresh-expiration}")
    private long REFRESH_EXPIRATION = 604800000;
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
       return buildToken(extraClaims, userDetails, EXPIRATION);
    }
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims, userDetails, REFRESH_EXPIRATION);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return generateRefreshToken(new HashMap<>(), userDetails);
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(Map<String, Object> claims, UserDetails user, long expiration){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
