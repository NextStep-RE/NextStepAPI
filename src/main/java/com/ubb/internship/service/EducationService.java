package com.ubb.internship.service;

import com.ubb.internship.dto.EducationDto;
import com.ubb.internship.dto.request.EducationRequestDto;
import com.ubb.internship.mapper.EducationMapper;
import com.ubb.internship.model.Education;
import com.ubb.internship.repository.EducationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    public List<EducationDto> getAllEducations() {
        return educationRepository.findAll().stream()
                .map(educationMapper::mapToDto)
                .toList();
    }

    public List<EducationDto> getEducationsByUserId(Long userId) {
        return educationRepository.findAllByUserId(userId).stream()
                .map(educationMapper::mapToDto)
                .toList();
    }

    public EducationDto addEducation(EducationRequestDto educationDto) {
        Education savedEducation = educationRepository.save(educationMapper.mapFromRequestDtoToModel(educationDto));
        return educationMapper.mapToDto(savedEducation);
    }

    public void deleteEducation(Long educationId) {
        if (!educationRepository.existsById(educationId)) {
            throw new EntityNotFoundException("Education not found");
        }
        educationRepository.deleteById(educationId);
    }

}
