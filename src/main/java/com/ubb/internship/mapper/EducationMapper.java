package com.ubb.internship.mapper;

import com.ubb.internship.dto.EducationDto;
import com.ubb.internship.dto.request.EducationRequestDto;
import com.ubb.internship.model.Education;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    EducationDto mapToDto(Education education);
    Education mapFromRequestDtoToModel(EducationRequestDto educationDto);

}
