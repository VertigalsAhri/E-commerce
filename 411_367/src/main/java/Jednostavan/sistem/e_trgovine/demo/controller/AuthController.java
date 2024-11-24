package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        
        User user = userRepository.findByUsername(username).orElse(null);
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Pogrešno korisničko ime ili lozinka");
            return "redirect:/login";
        }
        
        if (passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/products";
        }
        
        redirectAttributes.addFlashAttribute("error", "Pogrešno korisničko ime ili lozinka");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, 
                          BindingResult bindingResult, 
                          Model model) {
        
        // Postavi role prije validacije
        user.setRole("USER");
        
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        
        // Provjera da li korisničko ime već postoji
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Korisničko ime već postoji");
            return "auth/register";
        }
        
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("message", "Registracija uspješna! Možete se prijaviti.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Greška prilikom registracije: " + e.getMessage());
            return "auth/register";
        }
    }
} 