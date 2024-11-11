package com.example.dnd_log_microservice.LogRepository;


import com.example.dnd_log_microservice.LogModels.LogEntry;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LogRepository extends JpaRepository<LogEntry,String> {
}
