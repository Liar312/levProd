package com.example.dnd_log_microservice.Controllers;


import com.example.dnd_log_microservice.LogModels.LogEntryDTOForCard;
import com.example.dnd_log_microservice.Services.LogService;
import com.example.dnd_log_microservice.LogModels.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")

public class LogController {
    @Autowired
    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLog(@RequestBody LogEntryDTOForCard logEntryDTO) {
        try {
            LogEntry logEntry = logService.convertToLogEntry(logEntryDTO);
            logService.saveLog(logEntry);
            return ResponseEntity.ok("Log Save successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Log not saved");
        }

    }
}
