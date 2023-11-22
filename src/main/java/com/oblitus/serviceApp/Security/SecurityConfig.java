package com.oblitus.serviceApp.Security;

import com.oblitus.serviceApp.Modules.Admin.ERule;
import com.oblitus.serviceApp.Security.JWT.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private static final String[] WHITE_LIST = {
            "/**",
            "/auth/login",
            "/auth/register",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/actuator/**",
            
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST)
                                .permitAll()
//                                .requestMatchers("/adminModule/**").hasAnyAuthority(ERule.ADMIN.toString())
//                                .requestMatchers("/serviceModule/**").hasAnyAuthority(ERule.SERVICE.toString(), ERule.ADMIN.toString())
//                                .requestMatchers(HttpMethod.POST,"/serviceModule/tickets").hasAnyAuthority(ERule.CLIENT.toString())
//                                .requestMatchers(HttpMethod.POST,"/auth/logout").hasAnyAuthority(ERule.USER.toString())
//                                .requestMatchers(HttpMethod.POST,"/serviceModule/comments").hasAnyAuthority(ERule.CLIENT.toString())
//                                .requestMatchers(HttpMethod.PUT,"/serviceModule/comments/{id}").hasAnyAuthority(ERule.CLIENT.toString(),ERule.USER.toString())
//                                .requestMatchers(HttpMethod.PUT,"/serviceModule/tickets").hasAnyAuthority(ERule.CLIENT.toString())
//                                .requestMatchers(HttpMethod.PUT,"/adminModule/user/{id}").hasAnyAuthority(ERule.USER.toString())
//                                .requestMatchers(HttpMethod.PUT,"/serviceModule/tickets/{userId}").hasAnyAuthority(ERule.CLIENT.toString(),ERule.USER.toString())
//                                .requestMatchers(HttpMethod.GET,"/serviceModule/comments/**").hasAnyAuthority(ERule.CLIENT.toString(), ERule.USER.toString())
//                                .requestMatchers(HttpMethod.GET,"/serviceModule/comments").hasAnyAuthority(ERule.CLIENT.toString(), ERule.USER.toString())
//                                .requestMatchers(HttpMethod.GET,"/serviceModule/ticket/**").hasAnyAuthority(ERule.CLIENT.toString())
//                                .requestMatchers(HttpMethod.GET,"/serviceModule/activities/**").hasAnyAuthority(ERule.USER.toString())
//                                .requestMatchers(HttpMethod.GET,"/serviceModule/clients/{id}").hasAnyAuthority(ERule.USER.toString())
//                                .requestMatchers(HttpMethod.GET,"/files/{objectId}/{fileName}").hasAnyAuthority(ERule.USER.toString())
//                                .requestMatchers(HttpMethod.POST,"/files/{objectId}/upload").hasAnyAuthority(ERule.USER.toString())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> 
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );


        return http.build();
    }
}
