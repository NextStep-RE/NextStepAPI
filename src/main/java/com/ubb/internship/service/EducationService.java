package com.ubb.internship.service;

import com.ubb.internship.dto.EducationDto;
import com.ubb.internship.dto.request.EducationRequestDto;
import com.ubb.internship.mapper.EducationMapper;
import com.ubb.internship.model.Education;
import com.ubb.internship.model.User;
import com.ubb.internship.repository.EducationRepository;
import com.ubb.internship.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final UserRepository userRepository;

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
        User user = userRepository.findById(educationDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Education education = educationMapper.mapFromRequestDtoToModel(educationDto);
        education.setUser(user);
        Education savedEducation = educationRepository.save(education);

        return educationMapper.mapToDto(savedEducation);
    }

    public void deleteEducation(Long educationId) {
        if (!educationRepository.existsById(educationId)) {
            throw new EntityNotFoundException("Education not found");
        }
        educationRepository.deleteById(educationId);
    }

}
