package com.atm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserEntity  {

    public UserEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private String userId;

    @Column(name="u_first_name")
    private String userName;

    @Column(name="u_last_name")
    private String userLastName;

    @Column(name="u_number_passport")
    private String userNumberPassport;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userNumberPassport='" + userNumberPassport + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserNumberPassport() {
        return userNumberPassport;
    }

}
