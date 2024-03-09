package com.fdcend.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartOverviewDTO {

    private Long cartId;
    private List<Long> productsList;
    private Double totalCartPrice;

}
