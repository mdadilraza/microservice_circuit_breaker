package com.eidiko.integration_service.client;

import com.eidiko.integration_service.dto.PriceDto;
import com.eidiko.integration_service.fallback_factory.PriceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "price-service", url = "http://localhost:8083/api/price" ,fallbackFactory = PriceClientFallbackFactory.class)
public interface PriceClient {
    @GetMapping("{id}")
    PriceDto getPriceByProductId(@PathVariable Long id);
}