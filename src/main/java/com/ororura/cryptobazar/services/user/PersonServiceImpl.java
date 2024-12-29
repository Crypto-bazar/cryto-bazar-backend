package com.ororura.cryptobazar.services.user;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.Person;
import com.ororura.cryptobazar.repositories.PersonRepo;
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
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepo personRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Person findUserById(Long id) {
        return this.personRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public JWTResponse signIn(SignInDTO signInDTO) {
        JWTResponse response = new JWTResponse();
        Optional<Person> userEntity = this.personRepo.findByEmail(signInDTO.getEmail());

        if (userEntity.isEmpty()) {
            response.setToken(USER_NOT_FOUND);
            return response;
        }

        Person user = userEntity.get();

        if (!passwordEncoder.matches(signInDTO.getPassword(), user.getPassword())) {
            response.setToken(INVALID_PASSWORD);
            return response;
        }

        return generateToken(user);
    }

    @Override
    public JWTResponse signUp(SignUpDTO signUpDTO) {
        Person userEntity = new Person();

        userEntity.setEmail(signUpDTO.getEmail());
        userEntity.setUser(signUpDTO.getName());
        userEntity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        userEntity.setName(signUpDTO.getName());
        userEntity.setPhoneNumber(signUpDTO.getPhone_number());

        System.out.println(signUpDTO.getName());

        buildUser(signUpDTO);
        this.personRepo.save(userEntity);
        return generateToken(userEntity);
    }

    @Override
    public JWTResponse generateToken(Person userEntity) {
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
    public Person getUserByFirstName(String login) {
        return this.personRepo.findPersonByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> optionalUserEntity = personRepo.findByEmail(email);
        Person userEntity = optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException(email));

        return User.builder()
                .username(userEntity.getLogin())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().toString())
                .build();
    }
}