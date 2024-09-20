package com.ororura.cryptobazar.controllers;

import com.ororura.cryptobazar.entities.UserEntity;
import com.ororura.cryptobazar.services.userservice.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('user')")
    public UserEntity getUserByFirstName(@RequestParam String firstName) {
        return this.userService.getUserByFirstName(firstName);
    }
}
