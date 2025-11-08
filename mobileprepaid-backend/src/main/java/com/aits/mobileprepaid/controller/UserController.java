package com.aits.mobileprepaid.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.aits.mobileprepaid.dto.UserResponse;
import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userservice;

    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody @NonNull User user) {
        User saved = userservice.insert(user);
        UserResponse resp = new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getMobile(),
                saved.getRole()
        );
        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> fetchAllUsers() {
        List<UserResponse> users = userservice.fetchAllUsers().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getMobile(),
                        u.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
