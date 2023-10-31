package com.oblitus.serviceApp.Security.JWT;

import com.oblitus.serviceApp.Modules.Admin.UserRepository;
import com.oblitus.serviceApp.Modules.Admin.UserService;
import com.oblitus.serviceApp.Security.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository repository;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if(request.getServletPath().contains("/auth/**")){
            filterChain.doFilter(request,response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader == null ||  !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUserName(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userRepository.findByUsername(username).orElseThrow();
                var isTokenValid = repository.findByToken(jwt)
                        .map(token -> !token.isExpired() && !token.isRevoked())
                        .orElse(false);
            if(jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
