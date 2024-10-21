package com.example.testproject.Services;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    public Player getByLogin(String login){
        return playerRepository.findByLogin(login);
    }
    public void addPlayerByDTO(PlayerNameDto playerNameDto){
        Player player = new Player();
        player.setName(playerNameDto.getName());
        playerRepository.save(player);
        log.info("Player saved");
    }
    public Player findPlayerByLogin(String login){
        return playerRepository.findByLogin(login);
    }
}
