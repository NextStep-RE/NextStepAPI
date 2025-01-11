package com.ubb.internship.mapper;

import com.ubb.internship.dto.ApplicationDto;
import com.ubb.internship.dto.request.ApplicationRequestDto;
import com.ubb.internship.model.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    ApplicationDto mapToDto(Application application);
    Application mapFromRequestDtoToModel(ApplicationRequestDto applicationDto);

}
