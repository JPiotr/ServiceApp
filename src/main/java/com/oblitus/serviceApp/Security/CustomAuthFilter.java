package com.oblitus.serviceApp.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oblitus.serviceApp.Modules.Admin.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager am;
    CustomAuthFilter(AuthenticationManager authenticationManager){
        this.am = authenticationManager;
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        log.info("Username {}, password {}", username, password);
        UsernamePasswordAuthenticationToken at = new UsernamePasswordAuthenticationToken(username,password);
        return am.authenticate(at);
    }

    /**
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        TokenManager tokenManager = new TokenManager();
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokenManager.getTokens(user,request));
    }
}
