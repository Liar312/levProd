package com.example.testproject.Services;

import com.example.testproject.Models.Player;
import com.example.testproject.Models.PlayerCard;
import com.example.testproject.Models.PlayerNameDto;
import com.example.testproject.Repositories.PlayerCardRepository;
import com.example.testproject.Repositories.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.map;

@Service
@Slf4j
public class PlayerService implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;
@Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
private PlayerCardRepository playerCardRepository;


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

    // Метод для создания стартовой карточки
    private PlayerCard createStartingCard(Player player) {
        PlayerCard startingCard = new PlayerCard();
        startingCard.setCharacterName("Default Character");
        startingCard.setCharacterClass("Warrior");
        startingCard.setRace("Human");
        startingCard.setBackground("Newcomer");
        startingCard.setPlayer(player);
        return startingCard;
    }

    // Основной метод для добавления игрока
    public void addPlayerByDTO(PlayerNameDto playerNameDto) {
        // Создание нового игрока
        Player player = new Player();
        player.setName(playerNameDto.getName());
        String encodedPassword = passwordEncoder.encode(playerNameDto.getPassword());
        player.setPassword(encodedPassword);
        player.setLogin(playerNameDto.getLogin());

        // Создание стартовой карточки
        PlayerCard startingCard = createStartingCard(player);

        // Добавляем карточку в список карточек игрока
        List<PlayerCard> cardList = new ArrayList<>();
        cardList.add(startingCard);
        player.setPlayerCardList(cardList);

        // Сохранение игрока вместе с карточкой
        playerRepository.save(player);

        log.info("Player and starting PlayerCard saved");
    }


    @Transactional
    public void deletePlayerById(Long id){
        playerRepository.deleteById(id);
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
