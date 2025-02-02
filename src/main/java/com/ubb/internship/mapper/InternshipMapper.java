package com.ubb.internship.mapper;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.request.InternshipRequestDto;
import com.ubb.internship.model.Internship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternshipMapper {

    InternshipDto mapToDto(Internship internship);
    Internship mapFromRequestDtoToModel(InternshipRequestDto internshipDto);
}
