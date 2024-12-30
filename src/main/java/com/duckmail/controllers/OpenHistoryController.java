package com.duckmail.controllers;

import com.duckmail.dtos.openHistory.InOpenHistoryDTO;
import com.duckmail.services.OpenHistoryService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("open")
public class OpenHistoryController {
    private final OpenHistoryService openHistoryService;

    public OpenHistoryController(OpenHistoryService openHistoryService) {
        this.openHistoryService = openHistoryService;
    }

    @GetMapping("register")
    public ResponseEntity<Resource> createOpen(@RequestParam Long recipientId) throws Exception {
        openHistoryService.create(new InOpenHistoryDTO(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipientId));

        Path path = Paths.get("src/main/java/com/duckmail/assets/logo.png");
        Resource resource = new FileSystemResource(path.toFile());
        MediaType mediaType = MediaType.parseMediaType("image/png");

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(resource);
    }
}