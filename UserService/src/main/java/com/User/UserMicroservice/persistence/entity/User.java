package com.User.UserMicroservice.persistence.entity;

import com.User.UserMicroservice.enums.STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private STATUS accountStatus;
    private String password;

    private String postId = "1,2,3";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferenceId")
    private Preference preferences;

    public User() {
        this.preferences = new Preference();
        this.accountStatus = STATUS.ACTIVE;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", accountStatus=" + accountStatus +
                ", password='" + password + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}