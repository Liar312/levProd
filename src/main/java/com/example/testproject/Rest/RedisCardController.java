package com.example.testproject.Rest;

import com.example.testproject.Models.PlayerCard;
import com.example.testproject.Models.PlayerCardDTO;
import com.example.testproject.Redis.RedisCardRepository;
import com.example.testproject.Repositories.PlayerCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisCardController {
      @Autowired
      private RedisCardRepository redisCardRepository;
      @Autowired
      private PlayerCardRepository playerCardRepository;
    @RequestMapping(value = "/redis/add/card",method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody PlayerCardDTO playerCardDTO){
        PlayerCard playerCard = new PlayerCard();
        playerCard.setCharacterName(playerCardDTO.getKey());
        playerCard.setCharacterClass(playerCardDTO.getValue());
        playerCard.setRace(playerCardDTO.getRace());
        playerCardRepository.save(playerCard);
        redisCardRepository.add(playerCard);
        return ResponseEntity.ok("Player card with key(character name),value(character class),and race successfully created");
    }
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
