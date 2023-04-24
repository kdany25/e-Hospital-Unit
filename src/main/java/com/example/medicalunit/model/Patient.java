package com.example.medicalunit.model;

import com.example.medicalunit.model.Gender;

/**
 * Represents a patient in the hospital.
 */
public class Patient {
    private String id;
    private String name;
    private int age;
    private Gender gender;

    public Patient(String id, String name, int age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Gets the patient's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the patient's ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the patient's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the patient's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the patient's age.
     * Throws an IllegalArgumentException if the age is negative.
     */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        this.age = age;
    }

    /**
     * Gets the patient's gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the patient's gender.
     * Throws an IllegalArgumentException if the gender is not valid.
     */
    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null.");
        }
        this.gender = gender;
    }
}
