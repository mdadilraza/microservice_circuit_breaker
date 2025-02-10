package com.eidiko.integration_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceDto {
    private Long productId;
    private double price;
    private String currency;
}
