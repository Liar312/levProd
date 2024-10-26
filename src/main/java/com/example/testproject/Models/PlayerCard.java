package com.example.testproject.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long card_id;
    @Column(name = "characterName")
    private String characterName;
    @Column(name ="characterClass")
    private String characterClass;
    @Column(name= "race")
    private String race;
    @Column(name="background")
    private String background;
    @ElementCollection
    private Set<String> skills = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;
}
