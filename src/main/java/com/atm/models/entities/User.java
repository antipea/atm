package com.atm.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long userId;

    @Column(name = "u_first_name", nullable = false)
    private String userFirstName;

    @Column(name = "u_last_name", nullable = false)
    private String userLastName;

    @Column(name = "u_date_birth", nullable = false)
    private LocalDate userDateBirth;

    @Column(name = "u_number_passport", unique = true, nullable = false)
    private String userNumberPassport;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {

    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userDateBirth='" + userDateBirth + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserNumberPassport() {
        return userNumberPassport;
    }

    public LocalDate getUserDateBirth() {
        return userDateBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
