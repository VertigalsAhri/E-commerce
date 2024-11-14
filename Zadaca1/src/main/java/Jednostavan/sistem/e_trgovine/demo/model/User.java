package Jednostavan.sistem.e_trgovine.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Korisničko ime je obavezno")
    @Size(min = 3, max = 50, message = "Korisničko ime mora biti između 3 i 50 karaktera")
    private String username;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email mora biti validan")
    private String email;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 6, message = "Lozinka mora imati najmanje 6 karaktera")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    // Getteri i setteri
}
