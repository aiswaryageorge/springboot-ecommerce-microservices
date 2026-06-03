package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.ProductDTO;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.kafka.OrderProducer;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, ProductClient productClient,
                        OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        orderProducer.sendOrderEvent(
                "New Order Created with ID: " + savedOrder.getId()
        );
        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
    public ProductDTO getProductDetails(
            Long productId,
            String authorizationHeader
    ) {
        return productClient.getProductById(
                productId,
                authorizationHeader
        );
    }
    public ProductDTO fallbackProduct(Long productId, Exception ex) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productId);
        productDTO.setName("Product service unavailable");
        productDTO.setPrice(0.0);

        return productDTO;
    }
}