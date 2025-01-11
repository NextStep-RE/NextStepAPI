package com.ubb.internship.service;

import com.ubb.internship.dto.ExperienceDto;
import com.ubb.internship.dto.request.ExperienceRequestDto;
import com.ubb.internship.mapper.ExperienceMapper;
import com.ubb.internship.model.Company;
import com.ubb.internship.model.Experience;
import com.ubb.internship.model.User;
import com.ubb.internship.repository.CompanyRepository;
import com.ubb.internship.repository.ExperienceRepository;
import com.ubb.internship.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public List<ExperienceDto> getAllExperiences() {
        List<Experience> experiences = experienceRepository.findAll();
        List<ExperienceDto> experienceDtos = new ArrayList<>();

        experiences.forEach(experience -> {
            ExperienceDto experienceDto = attachCompanyDetailsToSpecificExperience(experience);
            experienceDtos.add(experienceDto);
        });

        return experienceDtos;
    }

    public List<ExperienceDto> getAllExperiencesByUserId(Long userId) {
        List<Experience> experiences = experienceRepository.findAllByUserId(userId);
        List<ExperienceDto> experienceDtos = new ArrayList<>();

        experiences.forEach(experience -> {
            ExperienceDto experienceDto = attachCompanyDetailsToSpecificExperience(experience);
            experienceDtos.add(experienceDto);
        });

        return experienceDtos;
    }

    public ExperienceDto addExperience(ExperienceRequestDto experienceDto) {
        Company company = companyRepository.findByCompanyName(experienceDto.getCompanyName())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        User user = userRepository.findById(experienceDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Experience experience = experienceMapper.mapFromRequestDtoToModel(experienceDto);
        experience.setCompany(company);
        experience.setUser(user);
        Experience savedExperience = experienceRepository.save(experience);

        return experienceMapper.mapToDto(savedExperience);
    }

    public void deleteExperience(Long experienceId) {
        if (!experienceRepository.existsById(experienceId)) {
            throw new EntityNotFoundException("Experience not found");
        }
        experienceRepository.deleteById(experienceId);
    }

    public ExperienceDto attachCompanyDetailsToSpecificExperience(Experience experience) {
        Company company = companyRepository.findById(experience.getCompany().getId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        ExperienceDto experienceDto = experienceMapper.mapToDto(experience);
        experienceDto.setCompanyName(company.getCompanyName());
        return experienceDto;
    }

}
