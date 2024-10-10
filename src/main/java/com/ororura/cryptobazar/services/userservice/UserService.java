package com.ororura.cryptobazar.services.userservice;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.repositories.UserRepo;
import com.ororura.cryptobazar.utils.JwtUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ororura.cryptobazar.services.userservice.ResponseStatus.INVALID_PASSWORD;
import static com.ororura.cryptobazar.services.userservice.ResponseStatus.USER_NOT_FOUND;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public JWTResponse signIn(SignInDTO signInDTO) {
        JWTResponse response = new JWTResponse();
        Optional<UserEntity> userEntity = this.userRepo.findByEmail(signInDTO.getEmail());

        if (userEntity.isEmpty()) {
            response.setToken(USER_NOT_FOUND);
            return response;
        }

        UserEntity user = userEntity.get();

        if (!passwordEncoder.matches(signInDTO.getPassword(), user.getPassword())) {
            response.setToken(INVALID_PASSWORD);
            return response;
        }

        return generateToken(user);
    }

    @Transactional
    public JWTResponse signUp(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setFirstName(signUpDTO.getFirstName());
        userEntity.setLastName(signUpDTO.getLastName());
        buildUser(signUpDTO);
        this.userRepo.save(userEntity);
        return generateToken(userEntity);
    }

    private JWTResponse generateToken(UserEntity u) {
        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setToken(jwtUtils.generateTokenFromUsername(u));
        return jwtResponse;
    }

    private void buildUser(SignUpDTO r) {
        User.builder()
                .username(r.getEmail())
                .roles("user")
                .password(passwordEncoder.encode(r.getPassword()))
                .build();
    }

    public UserEntity getUserByFirstName(String username) {
        return this.userRepo.findByFirstName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepo.findByEmail(email);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException(email));

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().toString())
                .build();
    }
}
