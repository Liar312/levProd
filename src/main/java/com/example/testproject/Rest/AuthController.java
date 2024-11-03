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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();//содержит информацию о текущей аутентификации
        if(auth == null){
            return null;
        }
        Object principal = auth.getPrincipal();
        Player player = (principal instanceof Player) ? (Player) principal : null;
        return Objects.nonNull(player) ? this.playerService.getByLogin(player.getLogin()) : null;
        }
    }

