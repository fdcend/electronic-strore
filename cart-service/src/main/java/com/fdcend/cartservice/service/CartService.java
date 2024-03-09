package com.fdcend.cartservice.service;

import com.fdcend.cartservice.dto.CartDTO;
import com.fdcend.cartservice.dto.ProductDTO;
import com.fdcend.cartservice.model.Cart;
import com.fdcend.cartservice.repository.ICartRepository;
import com.fdcend.cartservice.repository.IProductAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IProductAPI productAPI;


    //create a cart
    @Override
    public String addCart(Cart cart) {
        try {
            cartRepository.save(cart);
            //calculate total cart price
            getTotalCartPrice(cart.getCartId());
            return "Cart created successfully";
        } catch (Exception e) {
            System.out.println("Cant save cart info due to: " + e.getMessage());
            return "Cant save cart info due to: " + e.getMessage();
        }

    }

    //get cart by ID (DTO), it will show products details
    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "getCartByIdDTO_FallBack")
    @Retry(name = "products-service")
    public CartDTO getCartByIdDTO(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        CartDTO cartDTO = new CartDTO();

        List<ProductDTO> productList = new ArrayList<>();
        for (Long product : cart.getProductsList()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(productAPI.getProductById(product).getProductId());
            productDTO.setProductName(productAPI.getProductById(product).getProductName());
            productDTO.setProductBrand(productAPI.getProductById(product).getProductBrand());
            productDTO.setProductPrice(productAPI.getProductById(product).getProductPrice());
            productList.add(productDTO);
        }

        cartDTO.setCartId(cartId);
        cartDTO.setProductsList(productList);
        cartDTO.setTotalCartPrice(getTotalCartPrice(cartId));

        //createException();
        return cartDTO;
    }

    //fallback method
    public CartDTO getCartByIdDTO_FallBack(Throwable throwable) {
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return new CartDTO(null, null, null);
    }

    //get cart by ID, it will NOT show products details (overview)
    @Override
    public Cart getCartById(Long cartId) {
        try {
            return cartRepository.findById(cartId).orElse(null);
        } catch (Exception e) {
            System.out.println("Fallback method called due to: " + e.getMessage());
            return new Cart(null, null, null);
        }
    }

    //get all carts
    @Override
    public List<Cart> getAllCarts() {
        try {
            return cartRepository.findAll();
        } catch (Exception e) {
            System.out.println("Cant get the cart List due to: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    //add a product to the cart and update it
    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "addProductToCart_Fallback")
    @Retry(name = "products-service")
    public String addProductToCart(Long cartId, Long productId) {
        //getting the product by its ID
        ProductDTO product = productAPI.getProductById(productId);
        //getting the cart to edit
        Cart cart = this.getCartById(cartId);
        //getting the list of products from the cart
        List<Long> productListToEdit = cart.getProductsList();
        //adding product to the list
        productListToEdit.add(product.getProductId());
        //recalculate total cart price
        cart.setTotalCartPrice(getTotalCartPrice(cartId));
        //saving changes
        cartRepository.save(cart);

        //createException();
        return "Product added successfully";
    }

    //fallback method
    public String addProductToCart_Fallback(Throwable throwable) {
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return "Failed to add product to cart. Please try again later.";
    }

    //remove a product to the cart and update it
    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "deleteProductToCart_Fallback")
    @Retry(name = "products-service")
    public String deleteProductFromCart(Long cartId, Long productId) {
        //getting the product by its ID
        ProductDTO product = productAPI.getProductById(productId);
        //getting the cart to edit
        Cart cart = this.getCartById(cartId);
        //getting the list of products from the cart
        List<Long> productListToEdit = cart.getProductsList();
        //adding product to the list
        productListToEdit.remove(product.getProductId());
        //recalculate total cart price
        cart.setTotalCartPrice(getTotalCartPrice(cartId));
        //saving changes
        cartRepository.save(cart);

        //createException();
        return "Product deleted successfully";
    }

    //fallback method
    public String deleteProductToCart_Fallback(Throwable throwable) {
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return "Failed to delete product from cart. Please try again later.";
    }

    //get total price of products in the cart
    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "getTotalCartPrice_Fallback")
    @Retry(name = "products-service")
    public Double getTotalCartPrice(Long cartId) {

        Double totalPriceOfAllProducts = 0.0;
        Cart cart = this.getCartById(cartId);
        List<Long> productList = cart.getProductsList();

        for (Long product : productList) {
            totalPriceOfAllProducts += productAPI.getProductById(product).getProductPrice();
        }
        //set total cart price
        cart.setTotalCartPrice(totalPriceOfAllProducts);
        cartRepository.save(cart);

        //createException();
        return totalPriceOfAllProducts;
    }

    //fallback method
    public String getTotalCartPrice_Fallback(Throwable throwable) {
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return "Failed to get total cart price. Please try again later.";
    }

    //delete a cart
    @Override
    public String deleteCart(Long cartId) {
        try {
            cartRepository.deleteById(cartId);
            return "Cart deleted successfully";
        } catch (Exception e) {
            System.out.println("Cant delete cart due to: " + e.getMessage());
            return "Cant delete Cart due to: " + e.getMessage();
        }
    }

    //exception to test CircuitBreaker
    public void createException() {
        throw new RuntimeException("Cant Connect to product API");
    }

}
