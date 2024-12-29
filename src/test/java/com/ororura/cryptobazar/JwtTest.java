package com.ororura.cryptobazar;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.services.user.PersonServiceImpl;
import com.ororura.cryptobazar.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtTest {
    @Autowired
    private PersonServiceImpl userServiceImpl;

    @Autowired
    JwtUtils jwtUtils;

    @Test
    public void test() {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("john.doe@ororura.com");
        signUpDTO.setPassword("password");

        JWTResponse jwtResponse = userServiceImpl.signUp(signUpDTO);

        String userName = jwtUtils.getUserNameFromJwtToken(jwtResponse.getToken());
        assertEquals("john.doe@ororura.com", userName, "Users name not expected from JWT token");
        assertNotNull(jwtResponse.getToken(), "jwtResponse should not be null");
    }
}
