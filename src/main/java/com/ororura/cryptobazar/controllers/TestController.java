package com.ororura.cryptobazar.controllers;


import com.ororura.cryptobazar.entities.Person;
import com.ororura.cryptobazar.services.user.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    private final PersonServiceImpl userServiceImpl;

    public TestController(PersonServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<String> getUserByFirstName() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world");
    }
}
