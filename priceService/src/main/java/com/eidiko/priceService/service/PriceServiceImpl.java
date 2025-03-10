package com.eidiko.priceService.service;

import com.eidiko.priceService.entity.Price;
import com.eidiko.priceService.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{
    private final PriceRepository priceRepository;


    @Override
    public Price getPriceByProductId(long id) {
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return priceRepository.findByProductId(id);
    }
}
