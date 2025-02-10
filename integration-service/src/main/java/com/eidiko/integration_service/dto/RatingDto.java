package com.eidiko.integration_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingDto {
    private long productId;
    private int rating;
    private String review;
}
