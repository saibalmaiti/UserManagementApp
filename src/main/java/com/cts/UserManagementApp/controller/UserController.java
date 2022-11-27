package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUserById/{empId}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "empId") long empId) {
        User user = userService.getUserById(empId);
        if(user != null)
            return ResponseEntity.ok().body(user);
        return ResponseEntity.ok().body("User with user id "+empId+" not found");
    }
}
