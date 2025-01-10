package com.ubb.internship.controller;

import com.ubb.internship.dto.ExperienceDto;
import com.ubb.internship.dto.request.ExperienceRequestDto;
import com.ubb.internship.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<ExperienceDto>> getAllExperiences(@RequestParam(required = false) Long userId) {
        return userId == null ? ResponseEntity.ok(experienceService.getAllExperiences())
                : ResponseEntity.ok(experienceService.getAllExperiencesByUserId(userId));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ExperienceDto> createExperience(@RequestBody ExperienceRequestDto experienceDto) {
        ExperienceDto savedExperience = experienceService.addExperience(experienceDto);
        return ResponseEntity.created(new URI("/api/experiences/" + savedExperience.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

}
