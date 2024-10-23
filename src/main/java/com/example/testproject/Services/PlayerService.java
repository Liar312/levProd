package com.example.testproject.Services;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.map;

@Service
@Slf4j
public class PlayerService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    public void SavePlayer(Player player) {
        playerRepository.save(player);

    }

    //    public Player registerPlayer(String email,String password,String name){
//        if(playerRepository.findPlayerByEmail(email).isPresent()){
//            throw new IllegalArgumentException("Email уже используется");
//        }
//
//        Player player = new Player();
//        player.setEmail(email);
//        player.setName(name);
//        player.setPassword(password);
//        return playerRepository.save(player);
//    }
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Player getByLogin(String login) {
        return playerRepository.findByLogin(login);
    }

    public void addPlayerByDTO(PlayerNameDto playerNameDto) {
        Player player = new Player();
        player.setName(playerNameDto.getName());
        playerRepository.save(player);
        log.info("Player saved");
    }

    public Player findPlayerByLogin(String login) {
        return playerRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Player p = getByLogin(login);
        if(Objects.isNull(p)){
            throw new UsernameNotFoundException(String.format("User %s is not found",login));
        }
        Set<GrantedAuthority> authorities = p.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(
                p.getLogin(),
                p.getPassword(),
                true, true, true, true,
                authorities
        );
    }
}
