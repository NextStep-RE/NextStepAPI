package com.ubb.internship.controller;

import com.ubb.internship.dto.UserDto;
import com.ubb.internship.dto.request.UserRequestDto;
import com.ubb.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userDto) {
        UserDto savedUser = userService.createUser(userDto);
        return ResponseEntity.created(new URI("/api/users/" + savedUser.getId())).build();
    }
}
