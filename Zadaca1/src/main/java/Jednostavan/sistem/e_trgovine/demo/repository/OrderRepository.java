package Jednostavan.sistem.e_trgovine.demo.repository;

import Jednostavan.sistem.e_trgovine.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom query methods (if needed) can be added here
}
