package com.cts.UserManagementApp.controller;

import com.cts.UserManagementApp.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTests {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    void getUserSuccess() throws Exception {
        User user = new User(1L, "Saibal Maiti", "Saibal2");
        when(userService.getUserById(user.getEmpId())).thenReturn(user);
        assertEquals(user, userService.getUserById(user.getEmpId()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getUserById/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getUserFail() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getUserById/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
