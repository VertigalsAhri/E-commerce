package Jednostavan.sistem.e_trgovine.demo.service;

import Jednostavan.sistem.e_trgovine.demo.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
