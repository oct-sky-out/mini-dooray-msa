package com.nhnacademy.minidoorayuserapi.user.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("JOINED")
    @Column(name = "user_status", nullable = false)
    private UserStatus status;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("NOW()")
    private LocalDateTime createdAt;

    // Lombok의 EqualsAndHashCode는 메모리 누수를 야기함.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return userNo != null && Objects.equals(userNo, user.userNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
