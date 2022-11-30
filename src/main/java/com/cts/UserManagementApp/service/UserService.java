package com.cts.UserManagementApp.service;

import com.cts.UserManagementApp.model.ExJWT;
import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.model.UserDto;

import javax.servlet.ServletException;

public interface UserService {
    public boolean addUser(User user);
    public boolean validateUser(UserDto userDto);
    public User getUserById(long empId);

    public String generateToken(UserDto userDto) throws ServletException;
    public void logout(String token);
    public ExJWT getExpiredToken(String tokenId);
}
