package com.ororura.cryptobazar.controllers;


import com.ororura.cryptobazar.services.userservice.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserByFirstName(@RequestParam(required = false) String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserByFirstName(firstName));
    }
}
