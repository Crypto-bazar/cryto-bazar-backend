package com.ororura.cryptobazar;

import com.ororura.cryptobazar.controllers.TestController;
import com.ororura.cryptobazar.entities.user.Role;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.services.user.UserService;
import com.ororura.cryptobazar.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@MockBean(SecurityFilterChain.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;


    @Test
    void getUserByFirstName_ReturnsUser_WhenFirstNameIsProvided() throws Exception {
        UserEntity mockUser = new UserEntity(
                null,
                "vasya.ivanov@example.com",
                "securepassword",
                "Vasya",
                "Ivanov",
                Role.USER
        );

        Mockito.when(userService.getUserByFirstName(anyString())).thenReturn(mockUser);

        mockMvc.perform(get("/test")
                        .param("firstName", "Vasya")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"firstName\":\"Vasya\",\"lastName\":\"Ivanov\"}"));
    }

    @Test
    void getUserByFirstName_ReturnsBadRequest_WhenFirstNameIsMissing() throws Exception {
        mockMvc.perform(get("/test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
