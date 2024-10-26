package com.example.testproject.Config;

import com.example.testproject.Repositories.Security.JWTTokenRepository;
import com.example.testproject.Repositories.Security.JwtCsrfFilter;
import com.example.testproject.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final PlayerService service;
    private final JWTTokenRepository jwtTokenRepository;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public SecurityConfig(PlayerService service, JWTTokenRepository jwtTokenRepository,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.service = service;
        this.jwtTokenRepository = jwtTokenRepository;
        this.resolver = resolver;
    }

    @Bean
    public PasswordEncoder devPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                )
                .addFilterAt(new JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter.class)
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/auth/login")
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/login").authenticated()
                                .anyRequest().permitAll()
                )
                .httpBasic(httpBasic ->
                        httpBasic.authenticationEntryPoint((request, response, e) ->
                                resolver.resolveException(request, response, null, e)
                        )
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(service)
                .passwordEncoder(devPasswordEncoder());
        return authenticationManagerBuilder.build();
    }
}
