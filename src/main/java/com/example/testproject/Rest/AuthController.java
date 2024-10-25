package com.example.testproject.Rest;

import com.example.testproject.Models.Player;
import com.example.testproject.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PlayerService playerService;

    @PostMapping(path = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Player getAuthPlayer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//содержит информацию о текущей аутентификации
        if(authentication == null){
            return null;
        }
        Object principal = authentication.getPrincipal();//натягиваем текущуя аутентификацию на авторизированного пользователя
        Player player;
        if(principal instanceof Player){//если авторизованный пользователь является экземпляром класса юзер кидаем его к этому типу
            player = (Player) principal;
        } else{
            player = null;
        }
        if (Objects.nonNull(player)){
            return playerService.findPlayerByLogin(player.getLogin());
        } else {
            return null;
        }
    }
}
