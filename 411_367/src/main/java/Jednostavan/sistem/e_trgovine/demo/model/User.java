package Jednostavan.sistem.e_trgovine.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Korisničko ime ne može biti prazno")
    @Size(min = 3, max = 100, message = "Korisničko ime mora sadržavati između 3 i 100 karaktera")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Korisničko ime može sadržavati samo slova i brojeve")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Lozinka ne može biti prazna")
    @Size(min = 3, message = "Lozinka mora sadržavati najmanje 3 karaktera")
    private String password;

    @NotNull
    private String role = "USER";

    // Konstruktori
    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getteri i Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // toString metoda za lakši debug
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
