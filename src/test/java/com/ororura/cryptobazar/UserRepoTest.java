package com.ororura.cryptobazar;

import com.ororura.cryptobazar.entities.user.Role;
import com.ororura.cryptobazar.entities.user.UserEntity;
import com.ororura.cryptobazar.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    private UserEntity testUser;

    @BeforeEach
    void setup() {
        testUser = createTestUser();
    }

    @Test
    @Transactional
    void testGetUserByFirstName_Vasya() {

        saveUser(testUser);

        UserEntity retrievedUser = getUserByFirstName("Vasya");
        verifyUser(retrievedUser);
    }


    private UserEntity createTestUser() {
        return new UserEntity(
                null,
                "vasya.ivanov@example.com",
                "securepassword",
                "Vasya",
                "Ivanov",
                Role.USER
        );
    }

    private void saveUser(UserEntity user) {
        userRepo.save(user);
    }

    private UserEntity getUserByFirstName(String firstName) {
        return userRepo.findByFirstName(firstName);
    }

    private void verifyUser(UserEntity user) {
        assertNotNull(user, "User should not be null");
        assertEquals("Vasya", user.getFirstName(), "First name should be 'Vasya'");
        assertEquals("Ivanov", user.getLastName(), "Last name should be 'Ivanov'");
        assertEquals("vasya.ivanov@example.com", user.getEmail(), "Email should match");
        assertEquals("securepassword", user.getPassword(), "Password should match");
        assertEquals(Role.USER, user.getRole(), "Role should be 'USER'");
    }
}
