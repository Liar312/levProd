package com.example.testproject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @Column(name="login")
    private String login;
    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "player",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PlayerCard> playerCardList;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="player_roles",joinColumns = @JoinColumn(name= "player_id"))
    @Column(name = "role")
    private Set<Role> role;

}
