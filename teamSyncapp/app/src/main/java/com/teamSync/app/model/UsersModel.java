package com.teamSync.app.model;
import jakarta.persistence.*;
import com.teamSync.app.model.Role;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLEATHLETE;

    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordhash;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor required by JPA
    public UsersModel() {}

    public UsersModel(String firstname, String lastname, String email, String passwordhash, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.passwordhash = passwordhash;
        this.role = role;
    }

     @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordhash;
    }

    public void setPasswordHash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt; 
    }

    public LocalDateTime getUpdatedAt() { 
      return updatedAt; 
    }
    
    @Override
    public String toString() {
        return "UsersModel [" +
              "id=" + id +
              ", role=" + role +
              ", first_name=" + firstname +
              ", last_name=" + lastname +
              ", email=" + email +
              ", password_hash=" + passwordhash +
              ", updatedAt=" + updatedAt +
              ",createdAt=" + createdAt +
              "]";
    }

}
