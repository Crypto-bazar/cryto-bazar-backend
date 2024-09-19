package com.ororura.cryptobazar.services;

import com.ororura.cryptobazar.entities.UserEntity;
import com.ororura.cryptobazar.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    public UserEntity getUserByFirstName(String username) {
        return this.userRepo.findByFirstName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return user.get();
    }
}
