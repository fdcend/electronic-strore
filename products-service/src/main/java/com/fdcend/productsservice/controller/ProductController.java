package com.fdcend.productsservice.controller;

import com.fdcend.productsservice.model.Product;
import com.fdcend.productsservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Value("${server.port}")
    private int serverPort;


    //create a product
    @PostMapping("/add")
    public String addProductController(@RequestBody Product product) {
        System.out.println("port: " + serverPort);
        return productService.addProduct(product);
    }

    //get a product by ID
    @GetMapping("/get/{productId}")
    public Product getProductByIdController(@PathVariable Long productId) {
        System.out.println("port: " + serverPort);
        return productService.getProductById(productId);
    }

    //get all products
    @GetMapping("/get/all")
    public List<Product> getAllProductsController() {
        System.out.println("port: " + serverPort);
        return productService.getAllProducts();
    }

    //edit a product (won't change original ID)
    @PutMapping("/edit/{originalProductId}")
    public Product editProductByIdController(@PathVariable Long productId,
                                             @RequestParam String newProductName,
                                             @RequestParam String newProductBrand,
                                             @RequestParam Double newProductPrice)
    {
        System.out.println("port: " + serverPort);
        return productService.editProductById(productId, newProductName, newProductBrand, newProductPrice);
    }

    //delete a product
    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        System.out.println("port: " + serverPort);
        return productService.deleteProductById(productId);
    }
}