package com.eidiko.integration_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String currency;
    private List<RatingDto> ratings;
}
