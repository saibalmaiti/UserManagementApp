package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.model.UserDto;
import com.cts.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/v1/user")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @PostMapping("/addUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if(userService.addUser(user))
            return ResponseEntity.accepted().body("User Registered Successfully");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exists");
    }
    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody UserDto userDto) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            String jwtToken = userService.generateToken(userDto);
            responseMap.put("message", "User logged in successfully");
            responseMap.put("token", jwtToken);
        }
        catch (Exception e) {
            responseMap.put("message", "User not logged in successfully");
            responseMap.put("token",null);
        }
        return responseMap;
    }
}
