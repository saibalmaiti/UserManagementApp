package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
import com.cts.UserManagementApp.model.UserDto;
import com.cts.UserManagementApp.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationControllerTests {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private AuthenticationController authenticationController;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

    }
    @Test
    public void addUserSuccess() throws Exception {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userService.addUser(user)).thenReturn(true);
        assertEquals(HttpStatus.ACCEPTED, authenticationController.registerUser(user).getStatusCode());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/user/addUser").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isAccepted());
    }
    @Test
    public void addUserFail() throws Exception {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userService.addUser(any())).thenReturn(false);
        assertEquals(HttpStatus.CONFLICT, authenticationController.registerUser(user).getStatusCode());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/v1/user/addUser").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isConflict());
    }
    @Test
    void loginSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmpName("Saibal Maiti");
        userDto.setPassword("Saibal2");
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userService.validateUser(userDto)).thenReturn(true);
        when(userService.generateToken(userDto)).thenReturn("test_token");
        assertEquals("test_token", authenticationController.loginUser(userDto).get("token"));
    }
    @Test
    void loginFail() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.validateUser(any())).thenReturn(false);
        when(userService.generateToken(any())).thenReturn(null);
        assertNull(authenticationController.loginUser(userDto).get("token"));
    }
}
