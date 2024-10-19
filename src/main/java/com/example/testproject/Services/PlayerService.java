package com.example.testproject.Services;

import com.example.testproject.Models.Player;
import com.example.testproject.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void SavePlayer(Player player){
        playerRepository.save(player);

    }
    public Player registerPlayer(String email,String password,String name){
        if(playerRepository.findPlayerByEmail(email).isPresent()){
            throw new IllegalArgumentException("Email уже используется");
        }

        Player player = new Player();
        player.setEmail(email);
        player.setName(name);
        player.setPassword(password);
        return playerRepository.save(player);
    }
    public List<Player> getAll(){
        return playerRepository.findAll();
    }
}
