package net.javaguides.springboot.dto;

import javax.persistence.Column;
import java.util.List;

public class UserDTO {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private List<String> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
