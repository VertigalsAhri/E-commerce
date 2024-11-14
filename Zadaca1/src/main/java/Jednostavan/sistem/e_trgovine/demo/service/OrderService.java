package Jednostavan.sistem.e_trgovine.demo.service;

import Jednostavan.sistem.e_trgovine.demo.model.Order;
import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Narudžba nije pronađena"));
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);
        order.setQuantity(orderDetails.getQuantity());
        order.setStatus(orderDetails.getStatus());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
} 