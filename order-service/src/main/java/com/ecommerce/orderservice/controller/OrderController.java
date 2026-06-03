package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.ProductDTO;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
    @GetMapping("/product/{id}")
    public ProductDTO getProduct(
            @PathVariable Long id,
            @RequestHeader("Authorization")
            String authorizationHeader
    ) {

        return orderService.getProductDetails(
                id,
                authorizationHeader
        );
    }
}