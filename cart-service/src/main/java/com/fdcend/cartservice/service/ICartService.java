package com.fdcend.cartservice.service;

import com.fdcend.cartservice.dto.CartDTO;
import com.fdcend.cartservice.model.Cart;

import java.util.List;

public interface ICartService {

    //create a cart
    String addCart(Cart cart);

    //get cart by ID (DTO), it will show products details
    CartDTO getCartByIdDTO(Long cartId);

    //get cart by ID, it will NOT show products details
    Cart getCartById(Long cartId);

    //get all carts
    List<Cart> getAllCarts();

    //add a product to the cart and update it
    String addProductToCart(Long cartId, Long productId);

    //remove a product to the cart and update it
    String deleteProductFromCart(Long cartId, Long productId);

    //get total price of products in the cart
    Double getTotalCartPrice(Long cartId);

    //delete a cart
    String deleteCart(Long cartId);

}
