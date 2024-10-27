package com.ororura.cryptobazar.controllers;


import com.ororura.cryptobazar.services.user.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {
    private final UserServiceImpl userServiceImpl;

    public TestController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getUserByFirstName(@RequestParam(required = false) String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userServiceImpl.getUserByFirstName(firstName));
    }
}
