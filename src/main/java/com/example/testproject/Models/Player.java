package com.example.testproject.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="player_id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="email",unique = true)
    private String email;

    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL)
    private PlayerCard playerCard;
}
