package com.ubb.internship.mapper;

import com.ubb.internship.dto.UserDto;
import com.ubb.internship.dto.request.UserRequestDto;
import com.ubb.internship.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapToDto(User user);
    User mapFromRequestDtoToModel(UserRequestDto userDto);

}
