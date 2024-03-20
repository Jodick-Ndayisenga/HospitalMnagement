package com.example.metrocareclinic;

import androidx.annotation.NonNull;

public class User {
    String firstName, lastName, username, dob, email, residence, password, confirmPassword, specialities, isAdmin, role, id;

    public User(){

    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return  lastName;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }


    public String theUserName() {
        return username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail(){
        return  email;
    }

    public String getDob() {
        return dob;
    }

    public String getId() {
        return id;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getSpecialities() {
        return specialities;
    }

    public String getUsername() {
        return username;
    }

    public String getResidence() {
        return residence;
    }

    public String getRole() {
        return role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", residence='" + residence + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", specialities='" + specialities + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", role='" + role + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}