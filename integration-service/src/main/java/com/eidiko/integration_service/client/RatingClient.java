package com.eidiko.integration_service.client;

import com.eidiko.integration_service.dto.RatingDto;
import com.eidiko.integration_service.fallback_factory.RatingClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "rating-service",
        url = "http://localhost:8053/api/ratings"
       ,fallbackFactory = RatingClientFallbackFactory.class
        )
public interface RatingClient {
    @GetMapping("/{id}")
     ResponseEntity<List<RatingDto>> getRatingByProductId(@PathVariable long id);
}