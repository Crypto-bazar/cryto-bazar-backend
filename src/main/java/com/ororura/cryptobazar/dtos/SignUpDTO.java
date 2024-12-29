package com.ororura.cryptobazar.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO extends Auth {
    private String login;
    private String email;
    private String name;
    private String password;
    private String phone_number;
}
