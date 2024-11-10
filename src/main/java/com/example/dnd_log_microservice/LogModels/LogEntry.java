package com.example.dnd_log_microservice.LogModels;


import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name="logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String log_id;
    private String timestamp;
    private String level;
    private String message;
    private String serviceName;
    private Long cardId;
    private String CharacterName;


}
