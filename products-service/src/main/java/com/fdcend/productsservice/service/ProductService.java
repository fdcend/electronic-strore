package com.fdcend.productsservice.service;

import com.fdcend.productsservice.model.Product;
import com.fdcend.productsservice.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;


    //create a product
    @Override
    public String addProduct(Product product) {
        try {
            productRepository.save(product);
            return "Product created successfully";
        } catch (Exception e) {
            System.out.println("Cant add product due to: " + e.getMessage());
            return "Cant add product due to: " + e.getMessage();
        }
    }

    //get a product by ID
    @Override
    public Product getProductById(Long productId) {
        try {
            return productRepository.findById(productId).orElse(null);
        } catch (Exception e) {
            System.out.println("Cant get product due to: " + e.getMessage());
            return new Product(null, null, null, null);
        }
    }

    //get all products
    @Override
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cant get all products due to: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //edit a product (won't change original ID)
    @Override
    public Product editProductById(Long productId, String newProductName, String newProductBrand, Double newProductPrice) {
        try {
            Product productToEdit = this.getProductById(productId);

            productToEdit.setProductName(newProductName);
            productToEdit.setProductBrand(newProductBrand);
            productToEdit.setProductPrice(newProductPrice);

            productRepository.save(productToEdit);

            return this.getProductById(productId);
        } catch (Exception e) {
            System.out.println("Cant edit product due to: " + e.getMessage());
            return new Product(null, null, null, null);
        }
    }

    //delete a product
    @Override
    public String deleteProductById(Long productId) {
        try {
            productRepository.deleteById(productId);
            return "Product deleted successfully";
        } catch (Exception e) {
            System.out.println("Cant delete product due to: " + e.getMessage());
            return "Cant delete product due to: " + e.getMessage();
        }

    }
}
