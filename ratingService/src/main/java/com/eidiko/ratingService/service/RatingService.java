package com.eidiko.ratingService.service;

import com.eidiko.ratingService.entity.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getRatingByProductId(long id );
}
