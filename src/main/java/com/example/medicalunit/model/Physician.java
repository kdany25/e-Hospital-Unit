package com.example.medicalunit.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// Physician class that implements Comparable interface
public class Physician {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String role;
    private int age;
    private Gender gender;
    private String email;

    public Physician(String id, String firstName, String lastName, String userName, String email, String role, int age,
            Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.age = age;
        this.gender = gender;
    }
}
