package com.fdcend.salesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleOverviewDTO {

    private Long saleId;
    private Long cartId;
    private LocalDate saleDate;
    private List<Long> saleProductList;
    private Double salePrice;

}
