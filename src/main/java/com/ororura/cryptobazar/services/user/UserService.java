package com.ororura.cryptobazar.services.user;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    UserEntity findUserById(Long id);

    @Transactional
    JWTResponse signIn(SignInDTO signInDTO);

    @Transactional
    JWTResponse signUp(SignUpDTO signUpDTO);

    JWTResponse generateToken(UserEntity userEntity);

    void buildUser(SignUpDTO signUpDTO);

    UserEntity getUserByFirstName(String username);
}
