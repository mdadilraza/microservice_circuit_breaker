package com.example.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

//@Configuration
public class RouterConfig {
   // @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Product Service Route
                .route("product-service-route", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c.setName("productServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/product")
                                        )
                        )
                        .uri("lb://product-service")
                )

                // Rating Service Route
                .route("rating-service-route", r -> r
                        .path("/api/ratings/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c.setName("ratingServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/rating"))
                        )
                        .uri("lb://rating-service")
                )

                // Price Service Route
                .route("price-service-route", r -> r
                        .path("/api/price/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c.setName("priceServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/price"))
                        )
                        .uri("lb://price-service")
                )

                // Integration Service Route with StripPrefix
                .route("integration-service-route", r -> r
                        .path("/api/integration/**")
                        .filters(f -> f

                                .circuitBreaker(c -> c.setName("integrationServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/integration"))
                        )
                        .uri("lb://integration-service")
                )

                .build();
    }

}
