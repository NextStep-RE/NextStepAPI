package com.ubb.internship.service;

import com.ubb.internship.dto.ApplicationDto;
import com.ubb.internship.dto.request.ApplicationRequestDto;
import com.ubb.internship.mapper.ApplicationMapper;
import com.ubb.internship.model.Application;
import com.ubb.internship.model.Company;
import com.ubb.internship.model.Internship;
import com.ubb.internship.model.User;
import com.ubb.internship.model.enums.ApplicationStatusEnum;
import com.ubb.internship.repository.ApplicationRepository;
import com.ubb.internship.repository.InternshipRepository;
import com.ubb.internship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final InternshipRepository internshipRepository;
    private final UserRepository userRepository;

    public List<ApplicationDto> getAllApplications() {
        List<ApplicationDto> applications =  applicationRepository.findAll().stream()
                .map(applicationMapper::mapToDto)
                .toList();
        applications.forEach(this::attachCompanyDetailsToInternship);
        return applications;
    }

    public List<ApplicationDto> getApplicationsByUserId(Long userId) {
        List<ApplicationDto> applications = applicationRepository.findAllByUserId(userId).stream()
                .map(applicationMapper::mapToDto)
                .toList();
        applications.forEach(this::attachCompanyDetailsToInternship);
        return applications;
    }

    public ApplicationDto addApplication(ApplicationRequestDto applicationDto) {
        User user = userRepository.findById(applicationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Internship internship = internshipRepository.findById(applicationDto.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application application = applicationMapper.mapFromRequestDtoToModel(applicationDto);
        application.setDateApplied(new Date());
        application.setStatus(ApplicationStatusEnum.PENDING);
        application.setUser(user);
        application.setInternship(internship);

        Application savedApplication = applicationRepository.save(application);

        return applicationMapper.mapToDto(savedApplication);
    }

    private void attachCompanyDetailsToInternship(ApplicationDto application) {
        Internship internship = internshipRepository.findById(application.getInternship().getId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        Company company = internship.getCompany();

        application.getInternship().setCompanyWebsite(company.getWebsite());
        application.getInternship().setCompanyName(company.getCompanyName());
        application.getInternship().setCompanyLogoUrl(company.getLogoUrl());
    }

}
