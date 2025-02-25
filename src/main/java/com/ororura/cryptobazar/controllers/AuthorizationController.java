package com.ororura.cryptobazar.controllers;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.services.user.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
public class AuthorizationController {
    private final PersonServiceImpl userServiceImpl;

    public AuthorizationController(PersonServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/sign-up")
    public JWTResponse signUp(@RequestBody SignUpDTO signUpDTO) {
        return this.userServiceImpl.signUp(signUpDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JWTResponse> signIn(@RequestBody SignInDTO signInDTO) {
        JWTResponse jwtResponse = this.userServiceImpl.signIn(signInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}
