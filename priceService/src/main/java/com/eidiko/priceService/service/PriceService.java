package com.eidiko.priceService.service;


import com.eidiko.priceService.entity.Price;

public interface PriceService {
    Price getPriceByProductId(long id);

}
