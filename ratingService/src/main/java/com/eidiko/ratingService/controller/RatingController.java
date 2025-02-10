package com.eidiko.ratingService.controller;

import com.eidiko.ratingService.entity.Rating;
import com.eidiko.ratingService.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Rating>> getRatingByProductId(@PathVariable long id){
        List<Rating>rating = ratingService.getRatingByProductId(id);
        return ResponseEntity.ok(rating);
    }
}
