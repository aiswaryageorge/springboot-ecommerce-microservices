package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.config.FeignConfig;
import com.ecommerce.orderservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "PRODUCT-SERVICE",
        configuration = FeignConfig.class
)

public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductDTO getProductById(
            @PathVariable Long id,
            @RequestHeader("Authorization")
             String authorizationHeader
    );
}