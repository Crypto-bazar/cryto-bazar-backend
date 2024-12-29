package com.ororura.cryptobazar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpDTO extends Auth {
    private String login;
    private String email;
    private String name;
    private String password;
    private String phone_number;
    private LocalDate birthday;
}
