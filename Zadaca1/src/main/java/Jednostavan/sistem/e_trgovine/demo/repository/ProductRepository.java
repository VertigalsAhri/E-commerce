package Jednostavan.sistem.e_trgovine.demo.repository;

import Jednostavan.sistem.e_trgovine.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
