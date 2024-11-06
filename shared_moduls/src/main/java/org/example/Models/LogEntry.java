package org.example.Models;


import lombok.*;


import javax.persistence.*;


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


}
