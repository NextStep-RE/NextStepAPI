package com.ubb.internship.controller;

import com.ubb.internship.dto.EducationDto;
import com.ubb.internship.dto.request.EducationRequestDto;
import com.ubb.internship.service.EducationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<List<EducationDto>> getAllEducations(@RequestParam(required = false) Long userId) {
        return userId == null ? ResponseEntity.ok(educationService.getAllEducations())
                : ResponseEntity.ok(educationService.getEducationsByUserId(userId));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EducationDto> createEducation(@RequestBody EducationRequestDto educationDto) {
        EducationDto savedEducation = educationService.addEducation(educationDto);
        return ResponseEntity.created(new URI("/api/educations/" + savedEducation.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.noContent().build();
    }

}
