package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session,
                       Model model) {
        
        System.out.println("\n=== LOGIN ATTEMPT ===");
        System.out.println("Uneseni podaci:");
        System.out.println("Username: [" + username + "]");
        System.out.println("Password: [" + password + "]");
        
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user == null) {
            System.out.println("❌ Korisnik nije pronađen!");
            model.addAttribute("error", "Pogrešno korisničko ime ili lozinka");
            return "auth/login";
        }
        
        System.out.println("\nPronađeni podaci u bazi:");
        System.out.println("ID: " + user.getId());
        System.out.println("Username: [" + user.getUsername() + "]");
        System.out.println("Password: [" + user.getPassword() + "]");
        System.out.println("Role: " + user.getRole());
        
        // Direktno poređenje lozinki
        boolean passwordsMatch = password.equals(user.getPassword());
        System.out.println("\nProvjera lozinke:");
        System.out.println("Unesena lozinka: [" + password + "]");
        System.out.println("Lozinka u bazi: [" + user.getPassword() + "]");
        System.out.println("Poklapanje: " + (passwordsMatch ? "✅ DA" : "❌ NE"));
        
        if (passwordsMatch) {
            System.out.println("\n✅ Login uspješan!");
            session.setAttribute("user", user);
            return "redirect:/products";
        }
        
        System.out.println("\n❌ Pogrešna lozinka!");
        model.addAttribute("error", "Pogrešno korisničko ime ili lozinka");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
} 