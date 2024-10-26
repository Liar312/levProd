package com.example.testproject.Repositories.Security;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Services.PlayerService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@Repository
@Slf4j
public class JWTTokenRepository implements CsrfTokenRepository {
    PlayerService playerService;

//    public String userCheck(String login) {
//        if (playerService.findPlayerByLogin(login) != null) {
//            Player player = playerService.findPlayerByLogin(login);
//            String token = Jwts.builder()
//                    .setId(UUID.randomUUID().toString())
//                    .setSubject(player.getName())
//                    .setIssuedAt(new Date())
//                    .setExpiration(Date.from(LocalDateTime.now().plusHours(1)
//                            .atZone(ZoneId.systemDefault()).toInstant()))
//                    .signWith(SignatureAlgorithm.HS256, secret)
//                    .compact();
//
//            return token;//таким образом токен будет обновляться при каждом запросе(если задержка при запросе больше 30 мин то он станет не валидным)
//        } else {
//            throw new UsernameNotFoundException("пользователь не найден");
//
//        }
//    }

    @Getter
    private String secret;//ключ токена

    public JWTTokenRepository() {
        this.secret = "sptingtest";
    }

    @Override
    public DeferredCsrfToken loadDeferredToken(HttpServletRequest request, HttpServletResponse response) {
        return CsrfTokenRepository.super.loadDeferredToken(request, response);
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");//удаляем дефисы чтобы получить строку из 32 символов
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());//кидаем по разным типам установленное время жизни токена
        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)//время выпуска токена
                    .setNotBefore(now)//время с которого токен стал рабочим
                    .setExpiration(exp)//конец токена
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();//билдер токена
            log.info("Токен создан успшено");
        } catch (JwtException e) {
            log.info("Ошибка создания токена");
        }
        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        if(Objects.nonNull(csrfToken)){
            if(!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS))//проверка использования нужных заголовков на js
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS,csrfToken.getHeaderName());
            if(response.getHeaderNames().contains(csrfToken.getHeaderName()))
                response.setHeader(csrfToken.getHeaderName(),csrfToken.getToken());//обновляет существующее значение заголовка на getToken
            else
                response.addHeader(csrfToken.getHeaderName(),csrfToken.getToken());//тоже самое только добавляет
        }

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    public void clearToken(HttpServletResponse response){
        if(response.getHeaderNames().contains("x-csrf-token"))
            response.setHeader("x-csrf-token","");//очистка токена при ошибке авторизации
    }

}
