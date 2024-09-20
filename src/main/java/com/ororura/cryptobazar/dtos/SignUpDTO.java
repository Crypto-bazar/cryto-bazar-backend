package com.ororura.cryptobazar.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
