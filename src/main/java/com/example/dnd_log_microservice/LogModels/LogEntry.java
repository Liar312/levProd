package com.example.dnd_log_microservice.LogModels;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.generate.GeneratedMethod;


@Entity
@Table(name="logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String log_id;
    private String timestamp;
    private String level;
    private String message;
    private String serviceName;


}
