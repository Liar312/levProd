package com.example.testproject.Redis;

import com.example.testproject.Models.PlayerCard;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class RedisCardRepository implements RedisBaseRepository {
    private static final String KEY = "PlayerCard";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String,String,PlayerCard> hashOperations;

    @Autowired
    public RedisCardRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct

    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final PlayerCard playerCard) {
        hashOperations.put(KEY, playerCard.getCard_id().toString(), playerCard);
    }
    public void delete(final String id){
        hashOperations.delete(KEY,id);

    }
    public PlayerCard findPlayerCard(final Long id){
       return hashOperations.get(KEY,id);
    }
    public Map<String,PlayerCard> findAllPlayersCards(){
      return hashOperations.entries(KEY);
    }

}
