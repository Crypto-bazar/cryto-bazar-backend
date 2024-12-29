package com.ororura.cryptobazar.services.user;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.Person;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface PersonService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    Person findUserById(Long id);

    @Transactional
    JWTResponse signIn(SignInDTO signInDTO);

    @Transactional
    JWTResponse signUp(SignUpDTO signUpDTO);

    JWTResponse generateToken(Person userEntity);

    void buildUser(SignUpDTO signUpDTO);

    Person getUserByFirstName(String username);
}
