package com.example.fitclub.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    @Lob
    private String bio;

    @Override
    public String toString() {
        return "Profile[id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
}
