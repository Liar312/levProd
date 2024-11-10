package com.example.dnd_log_microservice.LogModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LogEntryDTOForCard {
    private String Message;
    private String TimeStamp;
    private String Level;
    private Long PlayerId;
    private String characterName;
    private String CharacterClass;
    private String Race;
}