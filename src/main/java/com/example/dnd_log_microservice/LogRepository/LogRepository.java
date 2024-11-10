package com.example.dnd_log_microservice.LogRepository;


import com.example.dnd_log_microservice.LogModels.LogEntry;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


public interface LogRepository extends CassandraRepository<LogEntry,String> {
}
