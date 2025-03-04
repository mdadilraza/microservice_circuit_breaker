package com.eidiko.integration_service.fallback_factory;

import com.eidiko.integration_service.client.RatingClient;
import com.eidiko.integration_service.dto.RatingDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class RatingClientFallbackFactory implements FallbackFactory<RatingClient> {
    @Override
    public RatingClient create(Throwable cause) {
        log.error("rating service is down. Reason: {}", cause.getMessage());
        return new RatingClient() {
            @Override
            public ResponseEntity<List<RatingDto>> getRatingByProductId(long id) {
                System.err.println("Fallback triggered for RatingService: " + cause);

                // Return default response
                return ResponseEntity.ok(Collections.emptyList());
            }

        };
    }
}

