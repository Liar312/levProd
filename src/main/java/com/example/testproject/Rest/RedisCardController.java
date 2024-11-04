package com.example.testproject.Rest;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerCard;
import com.example.testproject.Models.PlayerCardDTO;
import com.example.testproject.Redis.RedisCardRepository;
import com.example.testproject.Repositories.PlayerCardRepository;
import com.example.testproject.Services.PlayerService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisCardController {
      @Autowired
      private RedisCardRepository redisCardRepository;
      @Autowired
      private PlayerCardRepository playerCardRepository;
      @Autowired
      private PlayerService playerService;
        @RequestMapping(value = "/redis/add/card",method = RequestMethod.POST)
        public ResponseEntity<String> add(@RequestBody PlayerCardDTO playerCardDTO,Principal principal){
            Player player = playerService.findPlayerByLogin(principal.getName());
            if(player == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            PlayerCard playerCard = new PlayerCard();
            playerCard.setCharacterName(playerCardDTO.getKey());
            playerCard.setCharacterClass(playerCardDTO.getValue());
            playerCard.setRace(playerCardDTO.getRace());

            playerCard.setPlayer(player);
            playerCardRepository.save(playerCard);// в бд
            redisCardRepository.add(playerCard);//в редис
            Hibernate.initialize(playerCard.getSkills());
            return ResponseEntity.ok("Player card with key(character name),value(character class),and race successfully created");
        }//
    @Transactional
    @RequestMapping("/values")
    public @ResponseBody Map<String, String> findAll() {

        Map<String, PlayerCard> playerCards = redisCardRepository.findAllPlayersCards();


        Map<String, String> resultMap = new HashMap<>();


        for (Map.Entry<String, PlayerCard> entry : playerCards.entrySet()) {
            String key = entry.getKey();
            PlayerCard playerCard = entry.getValue();


            if (playerCard != null) {
                resultMap.put(key, playerCard.toString());
            } else {
                resultMap.put(key, "PlayerCard not found");
            }
        }

        return resultMap;
    }


}
