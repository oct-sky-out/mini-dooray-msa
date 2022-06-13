package com.nhnacademy.minidoorayuserapi.user.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.entity.User;
import com.nhnacademy.minidoorayuserapi.user.entity.UserStatus;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void userSignUpTest() {
        User user = User.builder()
            .id("id")
            .password("pw123")
            .email("id@abc.com")
            .status(UserStatus.JOINED)
            .createdAt(LocalDateTime.now())
            .build();
        entityManager.persist(user);

//        userRepository.saveAndFlush(user);

        userRepository.findById(user.getUserNo())
            .get()
            .equals(user);
    }

    @Test
    void findPasswordByUserIdTest() {
        UserPasswordDto userPasswordDto = userRepository.findPasswordByUserId("id").get();
        assertThat(userPasswordDto.getPassword()).isEqualTo("pw123");
    }

    @Test
    void findUserDetailsByIdTest() {
        UserDetailsDto userDetailsDto =userRepository.findUserDetailsById("id").get();
        assertThat(userDetailsDto.getId()).isEqualTo("id");
        assertThat(userDetailsDto.getPassword()).isEqualTo("pw123");
        assertThat(userDetailsDto.getStatus()).isEqualTo(UserStatus.JOINED);
    }

    @Test
    void findUserDetailsByEmail() {
        UserDetailsDto userDetailsDto = userRepository.findUserDetailByUserEmail("user1@nhnacademy.com")
            .get();
        assertThat(userDetailsDto.getId()).isEqualTo("user1");
        assertThat(userDetailsDto.getEmail()).isEqualTo("user1@nhnacademy.com");
    }
}
