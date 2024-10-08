package com.ororura.cryptobazar.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Auth {
       private String email;
       private String password;

}
