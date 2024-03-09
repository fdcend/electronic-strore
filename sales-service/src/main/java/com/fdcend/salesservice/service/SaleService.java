package com.fdcend.salesservice.service;

import com.fdcend.salesservice.dto.CartDTO;
import com.fdcend.salesservice.dto.SaleDTO;
import com.fdcend.salesservice.dto.SaleOverviewDTO;
import com.fdcend.salesservice.model.Sale;
import com.fdcend.salesservice.repository.ICartAPI;
import com.fdcend.salesservice.repository.ISaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private ICartAPI cartAPI;


    //create a sale
    @Override
    public String addSale(Sale sale) {
        try {
            saleRepository.save(sale);
            return "Sale created successfully";
        } catch (Exception e) {
            System.out.println("Cant add a sale due to: " + e.getMessage());
            return "Cant add a sale due to: " + e.getMessage();
        }
    }

    //get sale by ID, including detailed products info
    @Override
    @CircuitBreaker(name = "carts-service", fallbackMethod = "getSaleById_FallBack")
    @Retry(name = "carts-service")
    public SaleDTO getSaleById(Long saleId) {
        //find sale by ID
        Sale sale = saleRepository.findById(saleId).orElse(null);
        //find cart by sale ID
        CartDTO cart = cartAPI.getCartByID(saleId);
        //filling data from the sale(repository) and cart(API)
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setSaleId(sale.getSaleId());
        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setCartId(sale.getCartId());
        saleDTO.setSalePrice(cart.getTotalCartPrice());
        saleDTO.setSaleProductList(cart.getProductsList());

        //test exception
        //createException();
        return saleDTO;
    }

    //fallback method
    public SaleDTO getSaleById_FallBack(Throwable throwable) {
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return new SaleDTO(null, null, null, null, null);

    }

    //get all sales, not including detailed products info
    @Override
    @CircuitBreaker(name= "carts-service", fallbackMethod = "getAllSales_FallBack")
    @Retry(name= "carts-service")
    public List<SaleOverviewDTO> getAllSales() {
        List<Sale> listSales = saleRepository.findAll();
        List<SaleOverviewDTO> finalListDTO = new ArrayList<>();
        for (Sale sale : listSales) {
            SaleOverviewDTO saleToAdd = new SaleOverviewDTO();
            saleToAdd.setSaleId(sale.getSaleId());
            saleToAdd.setCartId(sale.getSaleId());
            saleToAdd.setSaleDate(sale.getSaleDate());
            saleToAdd.setSalePrice(cartAPI.getCartByIdOverview(sale.getCartId()).getTotalCartPrice());
            saleToAdd.setSaleProductList(cartAPI.getCartByIdOverview(sale.getCartId()).getProductsList());
            finalListDTO.add(saleToAdd);
        }

        //test exception
        //createException();
        return finalListDTO;
    }

    //fallback method
    public List<SaleOverviewDTO> getAllSales_FallBack(Throwable throwable){
        System.out.println("Cant get sales due to: " + throwable.getMessage());
        return  new ArrayList<>();
    }

    //edit sale (associated cart, date)
    @Override
    public String editSale(Long saleId, Long cartId, LocalDate saleDate) {
        try {
            Sale sale = saleRepository.findById(saleId).orElse(null);
            sale.setCartId(cartId);
            sale.setSaleDate(saleDate);
            saleRepository.save(sale);
            return "Sale edited successfully";
        } catch (Exception e) {
            System.out.println("Cant edit sale due to: " + e.getMessage());
            return "Cant edit sale due to: " + e.getMessage();
        }
    }

    //delete sale
    @Override
    public String deleteSale(Long saleId) {
        try {
            saleRepository.deleteById(saleId);
            return "Sale deleted successfully";
        } catch (Exception e) {
            System.out.println("Cant delete this sale due to: " + e.getMessage());
            return "Cant delete this sale due to: " + e.getMessage();
        }
    }

    //exception to test CircuitBreaker
    public void createException() {
        throw new RuntimeException("Cant Connect to product API");
    }

}
