package com.ubb.internship.service;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.InternshipListDto;
import com.ubb.internship.dto.InternshipSearchDto;
import com.ubb.internship.dto.request.InternshipRequestDto;
import com.ubb.internship.mapper.InternshipMapper;
import com.ubb.internship.model.Internship;
import com.ubb.internship.model.Company;
import com.ubb.internship.repository.InternshipRepository;
import com.ubb.internship.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final InternshipMapper internshipMapper;

    public InternshipListDto getAllInternships(InternshipSearchDto searchDTO, Integer offset, Integer limit) {
        Page<Internship> internshipsPage;
        InternshipListDto internshipListDto = new InternshipListDto();
        internshipListDto.setTotalNumber(internshipRepository.findAll().size());
        if (offset != null && limit != null && searchDTO != null) {
            Specification<Internship> spec = buildSearchSpecification(searchDTO);
            Pageable pageable = PageRequest.of(offset, limit);
            internshipsPage = internshipRepository.findAll(spec, pageable);
        } else if (offset != null && limit != null) {
            Pageable pageable = PageRequest.of(offset, limit);
            internshipsPage = internshipRepository.findAll(pageable);
        } else if (searchDTO != null) {
            Specification<Internship> spec = buildSearchSpecification(searchDTO);
            List<Internship> internshipList = internshipRepository.findAll(spec);
            internshipsPage = new PageImpl<>(internshipList);
        } else {
            List<Internship> internshipList = internshipRepository.findAll();
            internshipsPage = new PageImpl<>(internshipList);
        }

        List<InternshipDto> internships = attachCompanyDetailsToInternships(internshipsPage.getContent());
        internshipListDto.setInternships(internships);
        return internshipListDto;
    }

    public InternshipDto getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found with ID: " + id));

        return attachCompanyDetailToSpecificInternship(internship);
    }

    @Transactional
    public InternshipDto addInternship(InternshipRequestDto internshipDTO) {
        Company company = companyRepository.findById(internshipDTO.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + internshipDTO.getCompanyId()));

        Internship internship = internshipMapper.mapFromRequestDtoToModel(internshipDTO);  // Convert DTO to Model using Mapper
        internship.setCompany(company);
        internship.setDateAdded(new java.util.Date());

        Internship savedInternship = internshipRepository.save(internship);
        return internshipMapper.mapToDto(savedInternship);  // Return DTO
    }

    public void deleteInternship(Long id) {
        if (!internshipRepository.existsById(id)) {
            throw new EntityNotFoundException("Internship not found with ID: " + id);
        }
        internshipRepository.deleteById(id);
    }

    @Transactional
    public InternshipDto updateInternship(Long id, InternshipRequestDto internshipDTO) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found with ID: " + id));

        Company company = companyRepository.findById(internshipDTO.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + internshipDTO.getCompanyId()));

        internship.setTitle(internshipDTO.getTitle());
        internship.setCompany(company);
        internship.setDescription(internshipDTO.getDescription());
        internship.setExperience(internshipDTO.getExperience());
        internship.setLocation(internshipDTO.getLocation());
        internship.setStartDate(internshipDTO.getStartDate());
        internship.setEndDate(internshipDTO.getEndDate());
        internship.setApplicationDeadline(internshipDTO.getApplicationDeadline());
        internship.setRequirements(internshipDTO.getRequirements());

        Internship updatedInternship = internshipRepository.save(internship);
        return internshipMapper.mapToDto(updatedInternship);
    }

    public List<InternshipDto> attachCompanyDetailsToInternships(List<Internship> internships) {
        List<InternshipDto> result = new ArrayList<>();
        for (Internship internship : internships) {
            InternshipDto internshipDto = attachCompanyDetailToSpecificInternship(internship);
            result.add(internshipDto);
        }

        return result;
    }

    public InternshipDto attachCompanyDetailToSpecificInternship(Internship internship) {
        InternshipDto internshipDto = internshipMapper.mapToDto(internship);
        Company company = internship.getCompany();
        internshipDto.setCompanyName(company.getCompanyName());
        internshipDto.setCompanyLogoUrl(company.getLogoUrl());
        internshipDto.setCompanyWebsite(company.getWebsite());
        return internshipDto;
    }

    private Specification<Internship> buildSearchSpecification(InternshipSearchDto searchDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchDTO.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchDTO.getTitle() + "%"));
            }
            if (searchDTO.getCompanyName() != null) {
                predicates.add(criteriaBuilder.like(root.get("company").get("companyName"), "%" + searchDTO.getCompanyName() + "%"));
            }
            if (searchDTO.getLocation() != null) {
                predicates.add(criteriaBuilder.like(root.get("location"), "%" + searchDTO.getLocation() + "%"));
            }
            if (searchDTO.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchDTO.getStartDate()));
            }
            if (searchDTO.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchDTO.getEndDate()));
            }
            if (searchDTO.getApplicationDeadline() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("applicationDeadline"), searchDTO.getApplicationDeadline()));
            }
            if (searchDTO.getRequirements() != null && !searchDTO.getRequirements().isEmpty()) {
                predicates.add(root.get("requirements").in(searchDTO.getRequirements()));
            }

            if (searchDTO.getSortBy() != null) {
                if (searchDTO.isAscending()) {
                    query.orderBy(criteriaBuilder.asc(root.get(searchDTO.getSortBy())));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(searchDTO.getSortBy())));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
