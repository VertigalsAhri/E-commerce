package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.Order;
import Jednostavan.sistem.e_trgovine.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // GET method to show all orders
    @GetMapping
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/list"; // Refers to Thymeleaf template for displaying orders
    }

    // GET method to show the order form
    @GetMapping("/new") // Simplified mapping, no need to repeat 'orders'
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order()); // Add a new empty order to the model
        return "orders/form"; // Thymeleaf template for the order form
    }

    // POST method to create a new order
    @PostMapping
    public String createOrder(@ModelAttribute Order order) {
        orderRepository.save(order);  // Save the new order to the repository
        return "redirect:/orders";    // Redirect to the order list after saving
    }
}
