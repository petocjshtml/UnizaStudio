package org.example.unizastudio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String photo = "https://img.freepik.com/free-vector/smiling-young-man-illustration_1308-174669.jpg";

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin = false;
}

