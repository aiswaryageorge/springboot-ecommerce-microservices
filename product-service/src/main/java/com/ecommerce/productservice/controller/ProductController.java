package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.JwtService;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")

public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable Long id,
            @RequestHeader(
                    value = "Authorization",
                    required = false
            )
            String authHeader
    ) {

        System.out.println(
                "AUTH HEADER = "
                        + authHeader
        );
        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            return productService.getProductById(id);
        }
        String token =
                authHeader.substring(7);

        String username =
                jwtService.extractUsername(token);

        String role =
                jwtService.extractRole(token);

        System.out.println(
                "USERNAME = "
                        + username
        );

        System.out.println(
                "ROLE = "
                        + role
        );

        return productService.getProductById(id);
    }
}