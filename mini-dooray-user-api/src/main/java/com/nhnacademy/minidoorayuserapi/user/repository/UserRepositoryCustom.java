package com.nhnacademy.minidoorayuserapi.user.repository;

import com.nhnacademy.minidoorayuserapi.user.dto.UserBasicDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryCustom {
    Optional<UserPasswordDto> findPasswordByUserId(String id);

    Optional<UserDetailsDto> findUserDetailsById(String userId);

    Optional<UserDetailsDto> findUserDetailByUserEmail(String email);

    Page<UserBasicDto> findJoinedAllUserByPage(Long currentUserNo, Pageable pageable);
}
