package com.cts.UserManagementApp.service;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.model.UserDto;
import com.cts.UserManagementApp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void addUserSuccess() {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userRepo.findById(user.getEmpId())).thenReturn(Optional.empty());
        when(userRepo.save(user)).thenReturn(user);
        assertTrue(userService.addUser(user));
    }
    @Test
    public void addUserFail() {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userRepo.findById(user.getEmpId())).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        assertFalse(userService.addUser(user));
    }
    @Test
    public void validateUserSuccess() {
        User u = new User(1L, "Saibal Maiti", "Saibal2");
        UserDto user = new UserDto();
        user.setEmpName("Saibal Maiti");
        user.setPassword("Saibal2");
        when(userRepo.validateUser(user.getEmpName(), user.getPassword())).thenReturn(u);
        assertTrue(userService.validateUser(user));
    }
    @Test
    public void validateUserFail() {
        UserDto user = new UserDto();
        user.setEmpName("Saibal Maiti");
        user.setPassword("Saibal2");
        when(userRepo.validateUser(user.getEmpName(), user.getPassword())).thenReturn(null);
        assertFalse(userService.validateUser(user));
    }
    @Test
    public void getUserByIdSuccess() {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userRepo.findById(user.getEmpId())).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUserById(user.getEmpId()));
    }
}
