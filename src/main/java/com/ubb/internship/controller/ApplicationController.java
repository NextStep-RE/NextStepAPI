package com.ubb.internship.controller;

import com.ubb.internship.dto.ApplicationDto;
import com.ubb.internship.dto.request.ApplicationRequestDto;
import com.ubb.internship.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllApplications(@RequestParam(required = false) Long userId) {
        return userId == null ? ResponseEntity.ok(applicationService.getAllApplications())
                : ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody ApplicationRequestDto applicationDto) {
        ApplicationDto savedApplication = applicationService.addApplication(applicationDto);
        return ResponseEntity.created(new URI("/api/applications/" + savedApplication.getId())).build();
    }

}
