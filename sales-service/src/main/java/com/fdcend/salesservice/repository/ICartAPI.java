package com.fdcend.salesservice.repository;

import com.fdcend.salesservice.dto.CartDTO;
import com.fdcend.salesservice.dto.CartOverviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "carts-service")
public interface ICartAPI {

    @GetMapping("cart/get/{cartId}")
    public CartDTO getCartByID(@PathVariable Long cartId);

    @GetMapping("/cart/get/overview/{cartId}")
    public CartOverviewDTO getCartByIdOverview(@PathVariable Long cartId);

}
