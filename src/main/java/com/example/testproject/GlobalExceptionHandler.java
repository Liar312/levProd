package com.example.testproject;

import com.example.testproject.Repositories.Security.JWTTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.antlr.v4.runtime.atn.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private JWTTokenRepository jwtTokenRepository;
    public GlobalExceptionHandler(JWTTokenRepository jwtTokenRepository){
        this.jwtTokenRepository = jwtTokenRepository;
    }
    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    public ErrorInfo handlerAuthenticationException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response){
        this.jwtTokenRepository.clearToken(response);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return new ErrorInfo(UrlUtils.buildFullRequestUrl(request), "error.authorization");
    }
    @Getter public class ErrorInfo{
        private final String url;
        private final String info;

        public ErrorInfo(String url, String info) {
            this.url = url;
            this.info = info;
        }
    }
}
