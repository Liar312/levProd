package com.example.dnd_log_microservice.Controllers;


import com.example.dnd_log_microservice.LogModels.LogEntryDTOForCard;
import com.example.dnd_log_microservice.Services.LogService;
import com.example.dnd_log_microservice.LogModels.LogEntry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logs")
@NoArgsConstructor
public class LogController {
    @Autowired
    private LogService logService;


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
    @GetMapping("/find")
    public ResponseEntity<List<LogEntry>> gelAllLogsByUsername(@RequestBody Map<String,String> request){

        String username = request.get("username");//с мапы тянем по этому запросу значение
        List<LogEntry> logs = logService.getLogsByUsername(username);
        return ResponseEntity.ok(logs);
    }
}
