package com.example.testproject.Rest;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Services.PlayerService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        if(playerService.getByLogin(playerNameDto.getLogin())==null) {
            playerService.addPlayerByDTO(playerNameDto);//работает
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Пользователь с таким логином уже существует"
            );
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        playerService.deletePlayerById(id);
        return ResponseEntity.ok("Пользователь удалён успешно");
    }
//    @DeleteMapping("/delete/all")
//    public ResponseEntity<String> deleteAllUsers(){
//        playerService.
//    }

}

