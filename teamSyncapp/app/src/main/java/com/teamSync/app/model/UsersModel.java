package com.teamSync.app.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String first_name;
    private String last_name;
    private int year;
    private String email;
    private String password_hash;

    // Default constructor required by JPA
    public UsersModel() {}

    public UsersModel(String first_name, String last_name, int year, String email, String password_hash) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.year = year;
        this.email = email;
        this.password_hash = password_hash;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    
    @Override
    public String toString() {
        return "UsersModel [" +
              "id=" + id +
              ", role=" + role +
              ", first_name=" + first_name +
              ", last_name=" + last_name +
              ", year=" + year +
              ", email=" + email +
              ", password_hash=" + password_hash +
              "]";
    }

}
