package com.ororura.cryptobazar.services.user;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.repositories.UserRepo;
import com.ororura.cryptobazar.utils.JwtUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ororura.cryptobazar.services.user.ResponseStatus.INVALID_PASSWORD;
import static com.ororura.cryptobazar.services.user.ResponseStatus.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findUserById(Long id) {
        return this.userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
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

    @Override
    public JWTResponse signUp(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setUser(signUpDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setFirstName(signUpDTO.getFirstName());
        userEntity.setLastName(signUpDTO.getLastName());
        buildUser(signUpDTO);
        this.userRepo.save(userEntity);
        return generateToken(userEntity);
    }

    @Override
    public JWTResponse generateToken(UserEntity userEntity) {
        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setToken(jwtUtils.generateTokenFromUsername(userEntity));
        return jwtResponse;
    }

    @Override
    public void buildUser(SignUpDTO signUpDTO) {
        User.builder()
                .username(signUpDTO.getEmail())
                .roles("user")
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .build();
    }

    @Override
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
