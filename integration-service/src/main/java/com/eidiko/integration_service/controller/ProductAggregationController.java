package com.eidiko.integration_service.controller;
import com.eidiko.integration_service.client.PriceClient;
import com.eidiko.integration_service.client.RatingClient;
import com.eidiko.integration_service.dto.PriceDto;
import com.eidiko.integration_service.dto.ProductResponseDto;
import com.eidiko.integration_service.dto.RatingDto;
import com.eidiko.integration_service.service.ProductAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductAggregationController {
    private final ProductAggregationService aggregationService;
    private final RatingClient ratingClient;
    private final PriceClient priceClient;

    public ProductAggregationController(ProductAggregationService aggregationService, RatingClient ratingClient, PriceClient priceClient) {
        this.aggregationService = aggregationService;
        this.ratingClient = ratingClient;
        this.priceClient = priceClient;
    }

    //http://localhost:8084/products/details/1
    @GetMapping("/details/{productId}")
    public ResponseEntity<ProductResponseDto> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok(aggregationService.getProductWithDetails(productId));
    }

    //http://localhost:8084/products/getRating/1
    @GetMapping("/getRating/{id}")
    public ResponseEntity<List<RatingDto>> getRating(@PathVariable long id){
        List<RatingDto> ratingsByProduct = ratingClient.getRatingsByProduct(id);
        return ResponseEntity.ok(ratingsByProduct);
    }
    //http://localhost:8084/products/getPrice/1
    @GetMapping("/getPrice/{id}")
    public ResponseEntity<PriceDto>getPrice(@PathVariable long id){
        PriceDto priceByProductId = priceClient.getPriceByProductId(id);
        return ResponseEntity.ok(priceByProductId);
    }

}