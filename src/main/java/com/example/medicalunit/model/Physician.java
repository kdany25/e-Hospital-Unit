package com.example.medicalunit.model;

import com.example.medicalunit.model.Gender;

// Physician class that implements Comparable interface
public class Physician implements Comparable<Physician> {
    // Private fields
    private String id; // Unique ID of the physician
    private String name; // Name of the physician
    private int age; // Age of the physician
    private Gender gender; // Gender of the physician

    // Constructor that takes in arguments for all fields
    public Physician(String id, String name, int age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Getter method for ID field
    public String getId() {
        return id;
    }

    // Setter method for ID field
    public void setId(String id) {
        this.id = id;
    }

    // Getter method for name field
    public String getName() {
        return name;
    }

    // Setter method for name field
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for age field
    public int getAge() {
        return age;
    }

    // Setter method for age field
    public void setAge(int age) {
        this.age = age;
    }

    // Getter method for gender field
    public Gender getGender() {
        return gender;
    }

    // Setter method for gender field
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // Override compareTo method to sort by name field
    @Override
    public int compareTo(Physician other) {
        return this.name.compareTo(other.name);
    }
}
