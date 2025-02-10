package com.eidiko.integration_service.client;

import com.eidiko.integration_service.dto.ProductDto;
import com.eidiko.integration_service.fallback_factory.ProductClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service" ,url = "localhost:8081/api/products" ,fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable Long id);
}
