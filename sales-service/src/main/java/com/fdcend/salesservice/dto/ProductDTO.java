package com.fdcend.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProductDTO {

        private Long productId;
        private String productName;
        private String productBrand;
        private Double productPrice;

    }
