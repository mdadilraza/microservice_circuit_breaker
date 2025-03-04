package com.eidiko.priceService.controller;
import com.eidiko.priceService.entity.Price;
import com.eidiko.priceService.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
@AllArgsConstructor
public class PriceController {
    private final PriceService  priceService;

    @GetMapping("/{id}")
    public ResponseEntity<Price> getPriceByProductId(@PathVariable long id){
        Price price = priceService.getPriceByProductId(id);
        return ResponseEntity.ok(price);
    }
}
