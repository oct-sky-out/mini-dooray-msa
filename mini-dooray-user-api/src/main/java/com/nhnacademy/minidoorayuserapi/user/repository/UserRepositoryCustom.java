package com.nhnacademy.minidoorayuserapi.user.repository;

import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryCustom {
    Optional<UserPasswordDto> findPasswordByUserId(String id);

    Optional<UserDetailsDto> findUserDetailsByUserId (String userId);
}
