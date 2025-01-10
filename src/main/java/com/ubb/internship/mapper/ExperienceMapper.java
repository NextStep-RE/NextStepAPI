package com.ubb.internship.mapper;

import com.ubb.internship.dto.ExperienceDto;
import com.ubb.internship.dto.request.ExperienceRequestDto;
import com.ubb.internship.model.Experience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {

    ExperienceDto mapToDto(Experience experience);
    Experience mapFromRequestDtoToModel(ExperienceRequestDto experienceDto);

}
