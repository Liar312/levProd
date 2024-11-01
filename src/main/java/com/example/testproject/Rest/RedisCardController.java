package com.example.testproject.Rest;

import com.example.testproject.Models.PlayerCard;
import com.example.testproject.Redis.RedisCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisCardController {
      @Autowired
      private RedisCardRepository redisCardRepository;
    @RequestMapping(value = "/redis/add/user",method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestParam String key,@RequestParam  String value,@RequestParam String race){
        PlayerCard playerCard = new PlayerCard();
        playerCard.setCharacterName(key);
        playerCard.setCharacterClass(value);
        playerCard.setRace(race);
        redisCardRepository.add(playerCard);
        return ResponseEntity.ok("Player card with key(character name),value(character class),and race successfully created");
    }

}
