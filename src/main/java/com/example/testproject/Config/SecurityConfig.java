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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private PlayerService service;

    @Autowired
    private JWTTokenRepository jwtTokenRepository;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Bean
    public PasswordEncoder devPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                )
                .addFilterAt(new JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter.class)//укзываем что наш фильтр будет выполняться перед стандартныи csrf фильтром
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/**")//игнорируем обработку стандртного фильтра
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/login").authenticated()
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

