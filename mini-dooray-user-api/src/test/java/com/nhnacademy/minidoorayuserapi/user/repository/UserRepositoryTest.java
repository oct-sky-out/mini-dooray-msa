package com.nhnacademy.minidoorayuserapi.user.repository;

import static org.junit.jupiter.api.Assertions.*;

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
            .stats(UserStatus.JOINED)
            .createdAt(LocalDateTime.now())
            .build();
//        entityManager.persist(user);

        userRepository.saveAndFlush(user);

        userRepository.findById(user.getUserNo())
            .get()
            .equals(user);
    }
}
