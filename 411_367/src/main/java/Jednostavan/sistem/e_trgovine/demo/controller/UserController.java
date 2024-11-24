package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            return "redirect:/products";
        }
        
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, 
                           HttpSession session, 
                           RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            return "redirect:/products";
        }

        try {
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Korisnik je uspješno obrisan");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom brisanja korisnika");
        }
        
        return "redirect:/users";
    }
} 