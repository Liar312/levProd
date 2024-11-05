package com.example.dnd_log_microservice.Services;

import com.example.dnd_log_microservice.LogModels.LogEntry;
import com.example.dnd_log_microservice.LogRepository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveLog(LogEntry logEntry){
        logEntry.setLog_id(UUID.randomUUID().toString());
        logRepository.save(logEntry);
    }
}
