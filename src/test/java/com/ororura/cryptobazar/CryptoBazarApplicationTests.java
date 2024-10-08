package com.ororura.cryptobazar;

import com.ororura.cryptobazar.controllers.TestController;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.services.userservice.UserService;
import com.ororura.cryptobazar.utils.JwtUtils; // Импортируйте класс
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class CryptoBazarApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    public void testGetUserByFirstName_Vasya() throws Exception {
        UserEntity mockUser = new UserEntity();
        mockUser.setFirstName("Vasya");
        mockUser.setLastName("Ivanov");

        Mockito.when(userService.getUserByFirstName(anyString())).thenReturn(mockUser);

        mockMvc.perform(get("/test").param("firstName", "Vasya"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Vasya"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"));
    }
}
