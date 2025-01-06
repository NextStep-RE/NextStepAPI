package com.ubb.internship.service;

import com.ubb.internship.dto.UserDto;
import com.ubb.internship.dto.request.UserRequestDto;
import com.ubb.internship.mapper.UserMapper;
import com.ubb.internship.model.User;
import com.ubb.internship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    public UserDto getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDto)
                .orElse(null);
    }

    public UserDto getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(userMapper::mapToDto)
                .orElse(null);
    }

    public UserDto createUser(UserRequestDto userDto) {
        User savedUser = userRepository.save(userMapper.mapFromRequestDtoToModel(userDto));
        return userMapper.mapToDto(savedUser);
    }

}
