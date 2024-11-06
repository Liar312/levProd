package com.example.dnd_log_microservice.LogRepository;


import org.example.Models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntry,String> {
}
