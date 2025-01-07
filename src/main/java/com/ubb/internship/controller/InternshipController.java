package com.ubb.internship.controller;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.InternshipListDto;
import com.ubb.internship.dto.InternshipSearchDto;
import com.ubb.internship.dto.request.InternshipRequestDto;
import com.ubb.internship.service.InternshipService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<InternshipListDto> getAllInternships(@RequestParam(required = false) Integer offset,
                                                               @RequestParam(required = false) Integer limit,
                                                               @RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String companyName,
                                                               @RequestParam(required = false) String location,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date applicationDeadline,
                                                               @RequestParam(required = false) List<String> requirements,
                                                               @RequestParam(required = false) String sortBy,
                                                               @RequestParam(required = false) Boolean ascending) {
        InternshipSearchDto searchDto = new InternshipSearchDto();
        searchDto.setTitle(title);
        searchDto.setCompanyName(companyName);
        searchDto.setLocation(location);
        searchDto.setStartDate(startDate);
        searchDto.setEndDate(endDate);
        searchDto.setApplicationDeadline(applicationDeadline);
        searchDto.setRequirements(requirements);
        searchDto.setSortBy(sortBy);
        searchDto.setAscending(ascending != null && ascending);

        return ResponseEntity.ok(internshipService.getAllInternships(searchDto, offset, limit));
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
