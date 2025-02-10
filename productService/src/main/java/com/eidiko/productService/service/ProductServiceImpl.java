package com.eidiko.productService.service;

import com.eidiko.productService.entity.Product;
import com.eidiko.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public Product getProductById(long id) {
        log.info("id in service {}" ,id);
      return  productRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Product Not Found with the given id " + id));

    }
}
