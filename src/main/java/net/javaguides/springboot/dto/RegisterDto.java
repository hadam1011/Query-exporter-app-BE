package net.javaguides.springboot.dto;

import java.util.List;

public class RegisterDto {
    private String username;
    private String password;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    private List<String> roles;

    public RegisterDto(String username, String password, List<String> roles,
                       String fullName, String email, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.address = address;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}