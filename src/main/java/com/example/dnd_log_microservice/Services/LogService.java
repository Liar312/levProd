package com.example.dnd_log_microservice.Services;


import com.example.dnd_log_microservice.LogModels.LogEntryDTOForCard;
import com.example.dnd_log_microservice.LogRepository.LogRepository;
import com.example.dnd_log_microservice.LogModels.LogEntry;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
public class LogService {
    @Autowired
    private LogRepository logRepository;



    public void saveLog(LogEntry logEntry){
        logEntry.setLog_id(UUID.randomUUID().toString());
        logRepository.save(logEntry);
    }

    public LogEntry convertToLogEntry(LogEntryDTOForCard logEntryDTO){
        LogEntry logEntry = new LogEntry();
        logEntry.setMessage(logEntryDTO.getMessage());
        logEntry.setLevel(logEntryDTO.getLevel());
        logEntry.setTimestamp(logEntry.getTimestamp());
        logEntry.setCharacterName(logEntryDTO.getCharacterName());
        logEntry.setCardId(logEntry.getCardId());
        return logEntry;
    }
    public List<LogEntry> getLogsByUsername(String username){
        return logRepository.findAllByUsername(username);
    }
}
