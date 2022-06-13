package com.nhnacademy.minidoorayuserapi.user.repository;

import com.nhnacademy.minidoorayuserapi.user.dto.UserDetailsDto;
import com.nhnacademy.minidoorayuserapi.user.dto.UserPasswordDto;
import com.nhnacademy.minidoorayuserapi.user.entity.QUser;
import com.nhnacademy.minidoorayuserapi.user.entity.User;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<UserPasswordDto> findPasswordByUserId(String id) {
        QUser user = QUser.user;

        return Optional.ofNullable(from(user)
            .where(user.id.eq(id))
            .select(Projections.bean(UserPasswordDto.class, user.password))
            .fetchOne()
        );
    }

    @Override
    public Optional<UserDetailsDto> findUserDetailsById(String userId) {
        QUser user = QUser.user;

        UserDetailsDto userDetailsDto = from(user)
            .where(user.id.eq(userId))
            .select(Projections.bean(UserDetailsDto.class,
                user.userNo,
                user.id,
                user.password,
                user.email,
                user.status))
            .fetchOne();

        return Optional.ofNullable(userDetailsDto);
    }

    @Override
    public Optional<UserDetailsDto> findUserDetailByUserEmail(String email) {
        QUser user = QUser.user;

        UserDetailsDto userDetailsDto = from(user)
            .where(user.email.eq(email))
            .select(Projections.bean(UserDetailsDto.class,
                user.userNo,
                user.id,
                user.password,
                user.email,
                user.status))
            .fetchOne();

        return Optional.ofNullable(userDetailsDto);
    }


}
