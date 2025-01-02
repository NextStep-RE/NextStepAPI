package com.ubb.internship.controller;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.InternshipSearchDto;
import com.ubb.internship.model.Internship;
import com.ubb.internship.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public Page<InternshipDto> getAllInternships(InternshipSearchDto searchDTO, Pageable pageable) {
        return internshipService.getAllInternships(searchDTO, pageable);
    }

    @PostMapping
    public InternshipDto addInternship(@RequestBody InternshipDto internshipDto) {
        return internshipService.addInternship(internshipDto);
    }

    @PutMapping("/{id}")
    public InternshipDto updateInternship(@PathVariable Long id, @RequestBody InternshipDto internshipDto) {
        return internshipService.updateInternship(id, internshipDto);
    }

    @DeleteMapping("/{id}")
    public void deleteInternship(@PathVariable Long id) {
        internshipService.deleteInternship(id);
    }
}
