package com.example.testproject.Repositories;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerCardRepository extends JpaRepository<PlayerCard,Long> {
    List<PlayerCard> findByPlayer(Player player);
}
