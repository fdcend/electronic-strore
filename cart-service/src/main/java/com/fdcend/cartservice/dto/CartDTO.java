package com.fdcend.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//this DTO is used to show detailed products info for getCartById method.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long cartId;
    private List<ProductDTO> productsList;
    private Double totalCartPrice;

}
