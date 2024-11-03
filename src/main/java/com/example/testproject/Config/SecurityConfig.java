package com.example.testproject.Config;

import com.example.testproject.Repositories.Security.JWTTokenRepository;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/add/users", "/players","/delete/**").permitAll() // Разрешаем доступ без аутентификации
                                .requestMatchers("/auth/login").authenticated()       // Требуем аутентификацию для /auth/login
                                .anyRequest().permitAll()                             // Разрешаем все остальные запросы
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                )
                .addFilterAt(new JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter.class)
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/auth/login","/add/users","/delete/**","/redis/add/user","/redis/add/card")
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
        authenticationManagerBuilder.userDetailsService(service);
        return authenticationManagerBuilder.build();
    }
}