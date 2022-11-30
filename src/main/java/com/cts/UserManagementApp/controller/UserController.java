package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.response.ResponseHandler;
import com.cts.UserManagementApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUserById/{empId}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "empId") long empId) {
        User user = userService.getUserById(empId);
        CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.MINUTES);
        if(user != null)
            return ResponseEntity.ok().cacheControl(cacheControl)
                    .body(ResponseHandler.generateResponse("Successfully fetching the data", HttpStatus.OK,user));
//            return ResponseEntity.ok().body(user);
//        return ResponseEntity.ok().body("User with user id "+empId+" not found");
        return ResponseEntity.ok().cacheControl(cacheControl)
                .body(ResponseHandler.generateResponse("User with user id "+empId+" not found", HttpStatus.NOT_FOUND,null));
    }
    @PostMapping("/logout")
    private ResponseEntity<?> logOut(HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("authorization");
        String token = auth.substring(7);
        userService.logout(token);
        return ResponseEntity.accepted().body("Logout Successfully");
    }
}
