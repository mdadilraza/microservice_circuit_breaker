package com.eidiko.integration_service.fallback_factory;

import com.eidiko.integration_service.client.PriceClient;
import com.eidiko.integration_service.dto.PriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PriceClientFallbackFactory implements FallbackFactory<PriceClient> {
    @Override
    public PriceClient create(Throwable cause) {
        log.error("Product service is down. Reason: {}", cause.getMessage());
        return new PriceClient() {
            @Override
            public PriceDto getPriceByProductId(Long productId) {
                // Log the exception
                System.err.println("Fallback triggered for PriceService: " + cause);

                // Return default price response
                return new PriceDto(productId, 0.0, "INR");
            }
        };
    }
}
