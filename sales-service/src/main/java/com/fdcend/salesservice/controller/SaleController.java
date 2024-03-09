package com.fdcend.salesservice.controller;

import com.fdcend.salesservice.dto.SaleDTO;
import com.fdcend.salesservice.dto.SaleOverviewDTO;
import com.fdcend.salesservice.model.Sale;
import com.fdcend.salesservice.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    //create a sale
    @PostMapping("/add")
    public String addSale(@RequestBody Sale sale) {
        return saleService.addSale(sale);
    }

    //get sale by ID, including detailed products info
    @GetMapping("/get/{saleId}")
    public SaleDTO getSaleById(@PathVariable Long saleId) {
        return saleService.getSaleById(saleId);
    }

    //get all sales, not including detailed products info
    @GetMapping("/get/all")
    public List<SaleOverviewDTO> getAllSales() {
        return saleService.getAllSales();
    }

    //edit sale (associated cart, date)
    @PutMapping("/edit/{saleId}")
    public String editSale(@PathVariable Long saleId, @RequestParam Long cartId, @RequestParam LocalDate saleDate) {
        saleService.editSale(saleId, cartId, saleDate);
        return "Sale edited successfully";
    }

    //delete sale
    @DeleteMapping("/delete/{saleId}")
    public String deleteSale(@PathVariable Long saleId) {
        return saleService.deleteSale(saleId);
    }

}
