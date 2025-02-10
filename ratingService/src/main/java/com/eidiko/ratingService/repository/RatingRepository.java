package com.eidiko.ratingService.repository;

import com.eidiko.ratingService.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating ,Long> {
    List<Rating> findByProductId(long id);
}
