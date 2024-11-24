package Jednostavan.sistem.e_trgovine.demo.repository;

import Jednostavan.sistem.e_trgovine.demo.model.Order;
import Jednostavan.sistem.e_trgovine.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findAllByOrderByOrderDateDesc();
} 