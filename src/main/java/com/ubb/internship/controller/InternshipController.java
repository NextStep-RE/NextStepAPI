package com.ubb.internship.controller;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.InternshipSearchDto;
import com.ubb.internship.dto.request.InternshipRequestDto;
import com.ubb.internship.service.InternshipService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<List<InternshipDto>> getAllInternships(@RequestParam(required = false) Integer offset,
                                                                 @RequestParam(required = false) Integer limit,
                                                                 @RequestBody(required = false) InternshipSearchDto searchDTO) {
        List<InternshipDto> internships = internshipService.getAllInternships(searchDTO, offset, limit).getContent();
        return ResponseEntity.ok(internships);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<InternshipDto> addInternship(@RequestBody InternshipRequestDto internshipDto) {
        InternshipDto savedInternship = internshipService.addInternship(internshipDto);
        return ResponseEntity.created(new URI("/api/internships/" + savedInternship.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipDto> getInternshipById(@PathVariable Long id) {
        return ResponseEntity.ok(internshipService.getInternshipById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipDto> updateInternship(@PathVariable Long id,
                                                          @RequestBody InternshipRequestDto internshipDto) {
        return ResponseEntity.ok(internshipService.updateInternship(id, internshipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.noContent().build();
    }
}
