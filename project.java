package com.example.pharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PharmacyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyApplication.class, args);
    }

    // ==========================================
    // Models
    // ==========================================

    @Entity
    public static class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private double price;
        private int stock;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
    }

    @Entity
    public static class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long productId;
        private Long customerId;
        private Date orderDate;
        private int quantity;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public Date getOrderDate() { return orderDate; }
        public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // ==========================================
    // Repositories
    // ==========================================

    public interface ProductRepository extends JpaRepository<Product, Long> {}
    public interface OrderRepository extends JpaRepository<Order, Long> {}

    // ==========================================
    // Services
    // ==========================================

    @Service
    public static class ProductService {
        private final ProductRepository productRepository;

        public ProductService(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

        public Product createProduct(Product product) {
            return productRepository.save(product);
        }

        public Product getProductById(Long id) {
            Optional<Product> product = productRepository.findById(id);
            return product.orElse(null);
        }

        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }
    }

    @Service
    public static class OrderService {
        private final OrderRepository orderRepository;

        public OrderService(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        public List<Order> getAllOrders() {
            return orderRepository.findAll();
        }

        public Order createOrder(Order order) {
            return orderRepository.save(order);
        }

        public Order getOrderById(Long id) {
            Optional<Order> order = orderRepository.findById(id);
            return order.orElse(null);
        }

        public void deleteOrder(Long id) {
            orderRepository.deleteById(id);
        }
    }

    // ==========================================
    // Controllers
    // ==========================================

    @RestController
    @RequestMapping("/products")
    public static class ProductController {
        private final ProductService productService;

        public ProductController(ProductService productService) {
            this.productService = productService;
        }

        @GetMapping
        public List<Product> getAllProducts() {
            return productService.getAllProducts();
        }

        @PostMapping
        public Product createProduct(@RequestBody Product product) {
            return productService.createProduct(product);
        }

        @GetMapping("/{id}")
        public Product getProductById(@PathVariable Long id) {
            return productService.getProductById(id);
        }

        @DeleteMapping("/{id}")
        public void deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
        }
    }

    @RestController
    @RequestMapping("/orders")
    public static class OrderController {
        private final OrderService orderService;

        public OrderController(OrderService orderService) {
            this.orderService = orderService;
        }

        @GetMapping
        public List<Order> getAllOrders() {
            return orderService.getAllOrders();
        }

        @PostMapping
        public Order createOrder(@RequestBody Order order) {
            return orderService.createOrder(order);
        }

        @GetMapping("/{id}")
        public Order getOrderById(@PathVariable Long id) {
            return orderService.getOrderById(id);
        }

        @DeleteMapping("/{id}")
        public void deleteOrder(@PathVariable Long id) {
            orderService.deleteOrder(id);
        }
    }
}
