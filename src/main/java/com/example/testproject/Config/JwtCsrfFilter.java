package com.example.testproject.Config;

import com.example.testproject.Repositories.Security.JWTTokenRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.logging.Handler;

@AllArgsConstructor
public class JwtCsrfFilter extends OncePerRequestFilter {
    private final CsrfTokenRepository csrfTokenRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(HttpServletResponse.class.getName(), response);//свзязываем класс ответов с нашим ответом
        CsrfToken csrfToken = this.csrfTokenRepository.loadToken(request);
        boolean missingToken = csrfToken == null;//проверка на присутствие токена
        if (missingToken) {
            csrfToken = this.csrfTokenRepository.generateToken(request);
            this.csrfTokenRepository.saveToken(csrfToken, request, response);
        }
        request.setAttribute(CsrfToken.class.getName(), csrfToken);//тут смысл в том чтобы установить токен как параметр запроса чтобы обращаться к нему
        request.setAttribute(csrfToken.getParameterName(), csrfToken);

        String servletPath = request.getServletPath();
//        if(servletPath.equals("/players") || servletPath.equals("/add/users")||servletPath.startsWith("/delete/")||servletPath.equals("/auth/reg")
//         || servletPath.startsWith("/css/")||servletPath.startsWith("/js/")||servletPath.startsWith("/img/")||servletPath.startsWith("/templates/")||servletPath.startsWith("/auth/img")||servletPath.startsWith("/fonts/")){
//            filterChain.doFilter(request,response);
//            return;
//        }
        if(!servletPath.equals("/auth/login")){
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getServletPath().equals("/auth/login")) {
            try {
                filterChain.doFilter(request, response);//продолжаем прогон по фильтрам, в конце передаем на контроллер
            } catch (Exception e) {
                handlerExceptionResolver.resolveException(request, response, null, new MissingCsrfTokenException(csrfToken.getToken()));

            }
        } else {
            String actualToken = request.getHeader(csrfToken.getHeaderName());//если мы не идем на авторизацию то получаем токен как заголовок запроса
            if (actualToken == null) {
                actualToken = request.getParameter(csrfToken.getParameterName());
            }
            try {
                if (StringUtils.hasText(actualToken)) {
                    Jwts.parser()
                            .setSigningKey(((JWTTokenRepository) csrfToken).getSecret())//тянем секретку из репы
                            .parseClaimsJws(actualToken);
                    filterChain.doFilter(request, response);

                }
                else {
                    handlerExceptionResolver.resolveException(request,response,null, new InvalidCsrfTokenException(csrfToken,actualToken));
                }
            }
            catch (JwtException e){
                if(this.logger.isDebugEnabled()){
                    this.logger.debug("Invalid CSRF token found for" + UrlUtils.buildFullRequestUrl(request));//передаем полную ссылку запроса
                }
                if (missingToken) {
                    handlerExceptionResolver.resolveException(request, response, null, new MissingCsrfTokenException(actualToken));
                } else {
                    handlerExceptionResolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
                }
            }
        }

    }
}
