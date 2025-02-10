package com.eidiko.integration_service.service;

import com.eidiko.integration_service.dto.ProductResponseDto;

public interface ProductAggregationService {
    ProductResponseDto getProductWithDetails(Long productId);
}
