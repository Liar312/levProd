package com.example.testproject.Rest;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    @Autowired
    private PlayerService playerService;


    @GetMapping(value = "/players",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Player> getAll(){
        return playerService.getAll();
    }
    @PostMapping("/add/users")
    public void addPlayer(@RequestBody PlayerNameDto playerNameDto){
               playerService.addPlayerByDTO(playerNameDto);
    }

}

