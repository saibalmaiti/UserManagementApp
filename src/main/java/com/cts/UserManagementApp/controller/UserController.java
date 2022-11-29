package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.response.ResponseHandler;
import com.cts.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUserById/{empId}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "empId") long empId) {
        User user = userService.getUserById(empId);
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.HOURS);
        if(user != null)
            return ResponseEntity.ok().cacheControl(cacheControl)
                    .body(ResponseHandler.generateResponse("Successfully fetching the data", HttpStatus.OK,user));
//            return ResponseEntity.ok().body(user);
//        return ResponseEntity.ok().body("User with user id "+empId+" not found");
        return ResponseEntity.ok().cacheControl(cacheControl)
                .body(ResponseHandler.generateResponse("User with user id "+empId+" not found", HttpStatus.NOT_FOUND,null));
    }
}
