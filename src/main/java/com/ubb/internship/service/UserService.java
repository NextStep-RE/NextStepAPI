package com.ubb.internship.service;

import com.ubb.internship.dto.InternshipDto;
import com.ubb.internship.dto.LoginDto;
import com.ubb.internship.dto.UserDto;
import com.ubb.internship.dto.request.UserRequestDto;
import com.ubb.internship.mapper.UserMapper;
import com.ubb.internship.model.Internship;
import com.ubb.internship.model.User;
import com.ubb.internship.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InternshipService internshipService;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = attachCompanyDetailsToInternshipApplications(user);
            userDtos.add(userDto);
        }

        return userDtos;
    }

    public UserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return attachCompanyDetailsToInternshipApplications(user);
    }

    public UserDto getUserByEmailAndPassword(LoginDto loginDto) {
        User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return attachCompanyDetailsToInternshipApplications(user);
    }

    public UserDto createUser(UserRequestDto userDto) {
        User savedUser = userRepository.save(userMapper.mapFromRequestDtoToModel(userDto));
        return userMapper.mapToDto(savedUser);
    }

    private UserDto attachCompanyDetailsToInternshipApplications(User user) {
        UserDto userDto = userMapper.mapToDto(user);
        for (int i = 0; i < userDto.getApplications().size(); i++) {
            Internship internship = user.getApplications().get(i).getInternship();
            InternshipDto internshipDto = internshipService.attachCompanyDetailToSpecificInternship(internship);
            userDto.getApplications().get(i).setInternship(internshipDto);
        }

        return userDto;
    }

}
