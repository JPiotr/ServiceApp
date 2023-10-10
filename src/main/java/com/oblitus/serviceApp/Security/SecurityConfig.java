package com.oblitus.serviceApp.Security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService uds;
    private final BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uds).passwordEncoder(bcpe);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthFilter caf = new CustomAuthFilter(authenticationManagerBean());
        caf.setFilterProcessesUrl("/login/**");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //dependences
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/clients/**").hasAnyAuthority("BASE_USER");
//        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/clients/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/clients/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/clients/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/type/**").hasAnyAuthority("BASE_USER");
//        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/type/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/type/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/type/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/access/**").hasAnyAuthority("BASE_USER");
//        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/access/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/access/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/access/**").hasAnyAuthority("ADMIN", "SERVICE", "MODERATOR");
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilter((Filter) caf);
        http.addFilterBefore((Filter) new CustomAuthorizationFilter(), (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
