package com.fdcend.cartservice.controller;

import com.fdcend.cartservice.dto.CartDTO;
import com.fdcend.cartservice.model.Cart;
import com.fdcend.cartservice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;


    //create a cart
    @PostMapping("/add")
    public String addCart(@RequestBody Cart cart) {
        return cartService.addCart(cart);
    }

    //get cart by ID (DTO), it will show products details
    @GetMapping("/get/{cartId}")
    public CartDTO getCartById(@PathVariable Long cartId) {
        return cartService.getCartByIdDTO(cartId);
    }

    //get cart by ID, it will NOT show products details
    @GetMapping("/get/overview/{cartId}")
    public Cart getCartByIdOverview(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    //get all carts
    @GetMapping("/get/all")
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    //add a product to the cart and update it
    @PutMapping("edit/{cartId}/add-product/{productId}")
    public String addProductToCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId) {
        return cartService.addProductToCart(cartId, productId);
    }

    //remove a product to the cart and update it
    @PutMapping("edit/{cartId}/delete-product/{productId}")
    public String deleteProductFromCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId) {
        return cartService.deleteProductFromCart(cartId, productId);
    }

    //get total price of products in the cart
    @GetMapping("/get-total-price/{cartId}")
    public Double getTotalCartPrice(@PathVariable Long cartId) {
        return cartService.getTotalCartPrice(cartId);
    }

    //delete a cart
    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable Long cartId) {
        return cartService.deleteCart(cartId);
    }

}
