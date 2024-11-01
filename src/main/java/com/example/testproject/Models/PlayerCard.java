package com.example.testproject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerCard implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @JsonIgnoreProperties("playerCardList")
    @JsonIgnore
    private Player player;
}
