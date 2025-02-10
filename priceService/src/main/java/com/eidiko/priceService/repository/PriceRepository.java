package com.eidiko.priceService.repository;

import com.eidiko.priceService.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PriceRepository extends JpaRepository<Price,Long> {
   Price findByProductId(long id);
}
