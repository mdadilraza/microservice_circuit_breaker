//package com.eidiko.integration_service.service;
//import com.eidiko.integration_service.client.PriceClient;
//import com.eidiko.integration_service.client.ProductClient;
//import com.eidiko.integration_service.client.RatingClient;
//import com.eidiko.integration_service.dto.PriceDto;
//import com.eidiko.integration_service.dto.ProductDto;
//import com.eidiko.integration_service.dto.ProductResponseDto;
//import com.eidiko.integration_service.dto.RatingDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ProductAggregationServiceImpl implements ProductAggregationService {
//
//    private final ProductClient productClient;
//    private final RatingClient ratingClient;
//    private final PriceClient priceClient;
//
//    private final ExecutorService executorService =
//               Executors.newFixedThreadPool(3);
//
//    public ProductResponseDto getProductWithDetails(Long productId) {
//        log.info("Started at {}", System.nanoTime());
//
//        // Fetch product details asynchronously
//        CompletableFuture<ProductDto> productFuture = CompletableFuture.supplyAsync(
//                () -> productClient.getProductById(productId), executorService
//        );
//
//        // Fetch ratings asynchronously
//        CompletableFuture<List<RatingDto>> ratingsFuture = CompletableFuture.supplyAsync(
//                () -> ratingClient.getRatingsByProduct(productId), executorService
//        );
//
//        // Fetch price asynchronously
//        CompletableFuture<PriceDto> priceFuture = CompletableFuture.supplyAsync(
//                () -> priceClient.getPriceByProductId(productId), executorService
//        );
//
//
//        CompletableFuture<ProductResponseDto> responseFuture = productFuture.thenCombine(
//                ratingsFuture, (product, ratings) -> new ProductResponseDto(
//                        product.getId(),
//                        product.getName(),
//                        product.getDescription(),
//                        0.0,
//                        null,
//                        ratings
//                )
//        ).thenCombine(priceFuture, (response, price) -> {
//            response.setPrice(price.getPrice());
//            response.setCurrency(price.getCurrency());
//            return response;
//        });
//
//        ProductResponseDto response = responseFuture.join();
//        log.info("End at {}", System.nanoTime());
//
//        return response;
//    }
//}
package com.eidiko.integration_service.service;

import com.eidiko.integration_service.client.PriceClient;
import com.eidiko.integration_service.client.ProductClient;
import com.eidiko.integration_service.client.RatingClient;
import com.eidiko.integration_service.dto.PriceDto;
import com.eidiko.integration_service.dto.ProductDto;
import com.eidiko.integration_service.dto.ProductResponseDto;
import com.eidiko.integration_service.dto.RatingDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAggregationServiceImpl implements ProductAggregationService {

    private final ProductClient productClient;
    private final RatingClient ratingClient;
    private final PriceClient priceClient;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public ProductResponseDto getProductWithDetails(Long productId) {
        long start = System.currentTimeMillis();
        log.info("Started at {}", start);

        // Fetch product details asynchronously with Circuit Breaker
        CompletableFuture<ProductDto> productFuture = CompletableFuture.supplyAsync(
                () -> getProductById(productId), executorService
        );

        // Fetch ratings asynchronously with Circuit Breaker
        CompletableFuture<List<RatingDto>> ratingsFuture = CompletableFuture.supplyAsync(
                () -> getRatingsByProduct(productId), executorService
        );

        // Fetch price asynchronously with Circuit Breaker
        CompletableFuture<PriceDto> priceFuture = CompletableFuture.supplyAsync(
                () -> getPriceByProductId(productId), executorService
        );

        // Combine all results
        CompletableFuture<ProductResponseDto> responseFuture = productFuture.thenCombine(
                ratingsFuture, (product, ratings) -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        0.0,
                        null,
                        ratings
                )
        ).thenCombine(priceFuture, (response, price) -> {
            response.setPrice(price.getPrice());
            response.setCurrency(price.getCurrency());
            return response;
        });

        ProductResponseDto response = responseFuture.join();
        long end = System.currentTimeMillis();
        log.info("End at {}", end);

        log.info("took {} ms", end - start);

        return response;
    }

    // ---------------- Circuit Breaker Protected Methods ----------------

    //@CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public ProductDto getProductById(Long productId) {
        return productClient.getProductById(productId);
    }

   // @CircuitBreaker(name = "ratingService", fallbackMethod = "ratingFallback")
    public List<RatingDto> getRatingsByProduct(Long productId) {
        return ratingClient.getRatingByProductId(productId).getBody();
    }

   // @CircuitBreaker(name = "priceService", fallbackMethod = "priceFallback")
    public PriceDto getPriceByProductId(Long productId) {

        return priceClient.getPriceByProductId(productId);
    }

    // ---------------- Fallback Methods ----------------

    private ProductDto productFallback(Long productId, Throwable ex) {
        log.error("Product service is down! Returning default product.", ex);
        return new ProductDto(productId, "Unknown Product", "No Description");
    }

    private List<RatingDto> ratingFallback(Long productId, Throwable ex) {
        log.error("Rating service is down! Returning empty rating list.", ex);
        return Collections.emptyList();
    }

    private PriceDto priceFallback(Long productId, Throwable ex) {
        log.error("Price service is down! Returning default price.", ex);
        return new PriceDto(productId, 0.0, "INR");
    }
}
