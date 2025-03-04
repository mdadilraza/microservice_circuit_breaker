package com.example.api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/services")
    public Object getServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("/product")
    public ResponseEntity<String> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Product Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/rating")
    public ResponseEntity<String> ratingFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Rating Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/price")
    public ResponseEntity<String> priceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Price Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/integration")
    public ResponseEntity<String> integrationFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Integration Service is currently unavailable. Please try again later.");
    }
}
