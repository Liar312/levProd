package com.example.testproject.Redis;

import com.example.testproject.Models.PlayerCard;

import java.util.Map;

public interface RedisBaseRepository {
    Map<String,PlayerCard> findAllPlayersCards();
    void add(PlayerCard playerCard);
    void delete (String id);
    PlayerCard findPlayerCard(Long id);

}
