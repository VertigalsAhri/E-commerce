package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.Order;
import Jednostavan.sistem.e_trgovine.demo.model.OrderStatus;
import Jednostavan.sistem.e_trgovine.demo.model.Product;
import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.service.OrderService;
import Jednostavan.sistem.e_trgovine.demo.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public String listOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (user.getRole().equals("ADMIN")) {
            model.addAttribute("orders", orderService.getAllOrders());
        } else {
            model.addAttribute("orders", orderService.getUserOrders(user));
        }
        return "orders/list";
    }

    @GetMapping("/new/{productId}")
    public String showOrderForm(@PathVariable Long productId, Model model) {
        try {
            model.addAttribute("product", productService.getProductById(productId));
            model.addAttribute("order", new Order());
            return "orders/form";
        } catch (Exception e) {
            return "redirect:/products";
        }
    }

    @PostMapping("/new")
    public String createOrder(@RequestParam("product.id") Long productId,
                             @RequestParam("quantity") Integer quantity,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            // Dohvati proizvod
            Product product = productService.getProductById(productId);
            if (product == null) {
                redirectAttributes.addFlashAttribute("error", "Proizvod nije pronađen");
                return "redirect:/products";
            }

            // Kreiraj novu narudžbu
            Order order = new Order();
            order.setUser(user);
            order.setProduct(product);
            order.setQuantity(quantity);
            order.setOrderDate(LocalDateTime.now());
            
            // Sačuvaj narudžbu
            orderService.createOrder(order);
            
            redirectAttributes.addFlashAttribute("message", "Narudžba je uspješno kreirana!");
            return "redirect:/orders";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Greška prilikom kreiranja narudžbe: " + e.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Order order = orderService.getOrderById(id);

        if (!user.getRole().equals("ADMIN") && !order.getUser().equals(user)) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "orders/detail";
    }

    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable Long id,
                            @ModelAttribute Order orderDetails,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (!user.getRole().equals("ADMIN")) {
            return "redirect:/orders";
        }

        orderService.updateOrder(id, orderDetails);
        redirectAttributes.addFlashAttribute("message", "Narudžba je uspješno ažurirana!");
        return "redirect:/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        Order order = orderService.getOrderById(id);

        if (!user.getRole().equals("ADMIN") && !order.getUser().equals(user)) {
            return "redirect:/orders";
        }

        orderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("message", "Narudžba je uspješno obrisana!");
        return "redirect:/orders";
    }
} 