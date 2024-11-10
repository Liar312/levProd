package com.example.dnd_log_microservice.LogModels;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("logs") // Cassandra аннотация для определения таблицы
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEntry {
    @PrimaryKey // Cassandra аннотация для первичного ключа
    private String log_id;
    private String timestamp;
    private String level;
    private String message;
    private String serviceName;
    private Long cardId;
    private String characterName;
}
