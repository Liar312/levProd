package com.example.dnd_log_microservice.LogModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LogEntryDTO {
    private String message;
    private String timeStamp;
    private String level;
}
