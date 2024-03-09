package com.fdcend.cartservice.repository;

import com.fdcend.cartservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service")
public interface IProductAPI {

    @GetMapping("/products/get/{productId}")
    public ProductDTO getProductById(@PathVariable("productId") Long productId);
}