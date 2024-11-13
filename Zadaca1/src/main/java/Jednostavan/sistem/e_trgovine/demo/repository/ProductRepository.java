package Jednostavan.sistem.e_trgovine.demo.repository;

import Jednostavan.sistem.e_trgovine.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
