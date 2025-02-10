package com.eidiko.integration_service.fallback_factory;

import com.eidiko.integration_service.client.RatingClient;
import com.eidiko.integration_service.dto.RatingDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class RatingClientFallbackFactory implements FallbackFactory<RatingClient> {
    @Override
    public RatingClient create(Throwable cause) {
        log.error("Product service is down. Reason: {}", cause.getMessage());
        return new RatingClient() {
            @Override
            public List<RatingDto> getRatingsByProduct(Long productId) {
                // Log the exception
                System.err.println("Fallback triggered for RatingService: " + cause);

                // Return default response
                return Collections.emptyList();
            }
        };
    }
}

