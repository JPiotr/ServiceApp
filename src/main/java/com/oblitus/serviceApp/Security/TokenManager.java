//package com.oblitus.serviceApp.Security;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import javax.servlet.http.HttpServletRequest;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class TokenManager {
//    private static String secret = "Bearer ";
//    private static Algorithm algorithm = Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8));
//    private Map<String,String> tokens = new HashMap<>();
//
//    public TokenManager() {
//
//    }
//
//    private String generateAccessToken(User user, HttpServletRequest request){
//        return JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
//                .withIssuer(request.getRequestURI().toString())
//                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//    }
//
//    private String generateRefreshToken(User user, HttpServletRequest request){
//        return JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
//                .withIssuer(request.getRequestURI().toString())
//                .sign(algorithm);
//    }
//
//    public Map<String,String> getTokens(User user, HttpServletRequest request){
//        log.info("Getting tokens for user {}",user.getUsername());
//        tokens.put("access_token", generateAccessToken(user,request));
//        tokens.put("refresh_token", generateRefreshToken(user,request));
//        return tokens;
//    }
//
//    public static Algorithm getAlgorithm() {
//        return algorithm;
//    }
//}
