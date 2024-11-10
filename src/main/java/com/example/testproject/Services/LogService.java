package com.example.testproject.Services;

import com.example.testproject.DTO.LogEntryDTOForCard;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LogService {
    private final WebClient webClient;

    public LogService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build(); // URL логирующего микросервиса
    }

    public void sendLog(LogEntryDTOForCard logEntryDTO) {
        webClient.post()
                .uri("/logs")
                .bodyValue(logEntryDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
