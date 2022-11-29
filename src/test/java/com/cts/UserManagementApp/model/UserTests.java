package com.cts.UserManagementApp.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTests {
    User user = Mockito.mock(User.class);
    @Test
    void testempId() {
        when(user.getEmpId()).thenReturn(1L);
        assertEquals(1L, user.getEmpId());
    }
    @Test
    void testempName() {
        when(user.getEmpName()).thenReturn("Saibal Maiti");
        assertEquals("Saibal Maiti", user.getEmpName());
    }
    @Test
    void testPassword() {
        when(user.getPassword()).thenReturn("Saibal2");
        assertEquals("Saibal2", user.getPassword());
    }
}
