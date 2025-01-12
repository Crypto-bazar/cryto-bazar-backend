package com.ororura.cryptobazar.services.user;

import com.ororura.cryptobazar.dtos.JWTResponse;
import com.ororura.cryptobazar.dtos.SignInDTO;
import com.ororura.cryptobazar.dtos.SignUpDTO;
import com.ororura.cryptobazar.entities.Person;
import com.ororura.cryptobazar.repositories.PersonRepo;
import com.ororura.cryptobazar.utils.JwtUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Optional;

import static com.ororura.cryptobazar.services.user.ResponseStatus.INVALID_PASSWORD;

@Transactional
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
        Person userEntity = this.personRepo.findByLogin(signInDTO.getLogin());

        if (!passwordEncoder.matches(signInDTO.getPassword(), userEntity.getPassword())) {
            response.setToken(INVALID_PASSWORD);
            return response;
        }

        System.out.println();

        return generateToken(userEntity);
    }

    @Override
    public Person createUser(SignUpDTO signUpDTO) {
        Person person = new Person();

        person.setEmail(signUpDTO.getEmail());
        person.setUser(signUpDTO.getName());
        person.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        person.setName(signUpDTO.getName());
        person.setLogin(signUpDTO.getLogin());
        person.setPhoneNumber(signUpDTO.getPhone_number());
        person.setBirthDate(signUpDTO.getBirthday());

        return person;

    }

    @Override
    public JWTResponse signUp(SignUpDTO signUpDTO) {
        Person userEntity = createUser(signUpDTO);

        LocalDate currentDate = LocalDate.now();

        int age = Period.between(signUpDTO.getBirthday(), currentDate).getYears();

        if (age <= 18) {
            throw new IllegalArgumentException("Age must be less than 18");
        }
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
        return this.personRepo.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person userEntity = personRepo.findByLogin(email);
        return new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName())));
    }
}
