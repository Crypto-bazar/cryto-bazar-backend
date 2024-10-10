package com.ororura.cryptobazar.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO extends Auth {
    private String firstName;
    private String lastName;
}
