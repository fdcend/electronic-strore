package com.fdcend.salesservice.service;

import com.fdcend.salesservice.dto.CartDTO;
import com.fdcend.salesservice.dto.SaleDTO;
import com.fdcend.salesservice.dto.SaleOverviewDTO;
import com.fdcend.salesservice.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface ISaleService {

    //create a sale
    String addSale(Sale sale);

    //get sale by ID
    SaleDTO getSaleById(Long saleId);

    //get all sales, not including detailed products info
    List<SaleOverviewDTO> getAllSales();

    //edit sale (associated cart, date)
    String editSale(Long saleId, Long cartId, LocalDate saleDate);

    //delete sale
    String deleteSale(Long saleId);

}
