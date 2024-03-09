package com.fdcend.productsservice.service;

import com.fdcend.productsservice.model.Product;

import java.util.List;

public interface IProductService {

    //create a product
    String addProduct(Product product);

    //get a product by ID
    Product getProductById(Long productId);

    //get all products
    List<Product> getAllProducts();

    //edit a product (won't change original ID)
    Product editProductById(Long productId, String newProductName, String newProductBrand, Double newProductPrice);

    //delete a product
    String deleteProductById(Long productId);

}
