package com.example.dnd_log_microservice.LogModels;

import jakarta.persistence.*;
import lombok.*;


@Table(name="RedisCardLogs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String log_id;
    @Column(name="timestamp")
    private String timestamp;
    @Column(name="level")
    private String level;
    @Column(name="level")
    private String message;
    @Column(name="level")
    private String serviceName;
    @Column(name="cardId")
    private Long cardId;
    @Column(name="level")
    private String characterName;
    @Column(name="playerName")
    private String playerName;
}
