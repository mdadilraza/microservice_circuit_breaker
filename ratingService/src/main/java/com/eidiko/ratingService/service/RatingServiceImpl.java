package com.eidiko.ratingService.service;

import com.eidiko.ratingService.entity.Rating;
import com.eidiko.ratingService.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{
    private final RatingRepository ratingRepository;
    @Override
    public List<Rating> getRatingByProductId(long id) {
        return ratingRepository.findByProductId(id);
    }
}
