package com.ubb.internship.service;

import com.ubb.internship.dto.ExperienceDto;
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
    private final ExperienceService experienceService;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        users.forEach(user -> {
            UserDto userDto = attachCompanyDetailsToInternshipApplications(user);
            attachCompanyDetailsToSpecificInternshipApplications(user, userDto);
            userDtos.add(userDto);
        });

        return userDtos;
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserDto userDto = attachCompanyDetailsToInternshipApplications(user);
        attachCompanyDetailsToSpecificInternshipApplications(user, userDto);
        return userDto;
    }

    public UserDto getUserByEmailAndPassword(LoginDto loginDto) {
        User user = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserDto userDto = attachCompanyDetailsToInternshipApplications(user);
        attachCompanyDetailsToSpecificInternshipApplications(user, userDto);
        return userDto;
    }

    public UserDto createUser(UserRequestDto userDto) {
        User savedUser = userRepository.save(userMapper.mapFromRequestDtoToModel(userDto));
        return userMapper.mapToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setBio(userDto.getBio());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());

        return userMapper.mapToDto(userRepository.save(user));
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

    private void attachCompanyDetailsToSpecificInternshipApplications(User user, UserDto userDto) {
        List<ExperienceDto> experienceDtos = new ArrayList<>();
        user.getExperiences().forEach(experience -> {
            ExperienceDto experienceDto = experienceService.attachCompanyDetailsToSpecificExperience(experience);
            experienceDtos.add(experienceDto);
        });

        userDto.setExperiences(experienceDtos);
    }

}
