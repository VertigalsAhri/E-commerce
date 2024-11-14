package Jednostavan.sistem.e_trgovine.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import Jednostavan.sistem.e_trgovine.demo.model.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Korisnik je obavezan")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Proizvod je obavezan")
    private Product product;

    @NotNull(message = "Količina je obavezna")
    @Min(value = 1, message = "Količina mora biti najmanje 1")
    private Integer quantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    // Konstruktori
    public Order() {}

    public Order(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.calculateTotalPrice();
    }

    // Getteri i Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { 
        this.product = product;
        this.calculateTotalPrice();
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
        this.calculateTotalPrice();
    }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    // Helper metode
    private void calculateTotalPrice() {
        if (product != null && quantity != null) {
            this.totalPrice = product.getPrice() * quantity;
        }
    }
} 