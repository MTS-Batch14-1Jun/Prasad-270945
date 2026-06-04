package com.techacademy.trainbase.controller;

import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryDataJpaTest {

    @Autowired
    private UserRepository userRepository;

    /*@Test
    void givenUser_whenSave_thenFindByEmailReturnsUser() {
        // GIVEN
        User user = new User();
        user.setUsername("Repository User");
        user.setEmail("repo-user@example.com");
        user.setPassword("password");
        user.setRole("role");
        userRepository.saveAndFlush(user);

        // WHEN
        Optional<User> result = userRepository.findByEmail("repo-user@example.com");

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("Repository User");
    }*/

    @Test
    void givenUnknownEmail_whenFindByEmail_thenReturnEmpty() {
        // WHEN
        Optional<User> result = userRepository.findByEmail("missing@example.com");

        // THEN
        assertThat(result).isEmpty();
    }
}