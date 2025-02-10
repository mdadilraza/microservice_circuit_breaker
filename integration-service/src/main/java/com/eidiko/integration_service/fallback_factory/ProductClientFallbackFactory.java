package com.eidiko.integration_service.fallback_factory;

import com.eidiko.integration_service.client.ProductClient;
import com.eidiko.integration_service.dto.ProductDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {
    @Override
    public ProductClient create(Throwable cause) {
        log.error("Product service is down. Reason: {}", cause.getMessage());
        return new ProductClient() {
            @Override
            public ProductDto getProductById(Long productId) {
                // Log the exception
                System.err.println("Fallback triggered for ProductService: " + cause);

                // Return default product response
                return new ProductDto(productId, "Unknown Product", "No Description");
            }
        };
    }
}

