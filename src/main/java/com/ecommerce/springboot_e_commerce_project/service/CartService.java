package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.dto.CartItemDTO;
import com.ecommerce.springboot_e_commerce_project.dto.CartResponseDTO;
import com.ecommerce.springboot_e_commerce_project.mapper.CartItemMapper;
import com.ecommerce.springboot_e_commerce_project.mapper.CartMapper;
import com.ecommerce.springboot_e_commerce_project.model.Cart;
import com.ecommerce.springboot_e_commerce_project.model.CartItem;
import com.ecommerce.springboot_e_commerce_project.model.Product;
import com.ecommerce.springboot_e_commerce_project.repo.CartItemRepository;
import com.ecommerce.springboot_e_commerce_project.repo.CartRepository;
import com.ecommerce.springboot_e_commerce_project.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CartItemRepository cartItemRepo;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;

    public void addToCart(int cartId, int productId, int quantity) {

//        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Cart cart = cartRepo.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            return cartRepo.save(newCart);
        });
        Product product =  productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> item = cartItemRepo.findByCartAndProduct(cart,product);

        CartItem cartItem;
        if(item.isPresent()){
            cartItem = item.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else{
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getProductPrice());
            cartItem.setQuantity(quantity);
        }
        cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItemRepo.save(cartItem);
        updateCartTotals(cart);
    }

    public ResponseEntity<CartResponseDTO> getCartItems(int cartId) {

        try{
            Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
            return new ResponseEntity<>(cartMapper.toDTO(cart),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public CartItemDTO updateCart(int cartId, int productId, int quantity) {

        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not exists"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not exists"));
        CartItem cartItem = cartItemRepo.findByCartAndProduct(cart,product).orElseGet(()->{
            CartItem item = new CartItem();
            return cartItemRepo.save(item);
        });

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getProductPrice());
        CartItem savedItem = cartItemRepo.save(cartItem);

        updateCartTotals(cart);
        return cartItemMapper.toDTO(savedItem);

    }

    public boolean removeItem(int cartId, int productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not exists"));
        Product product = productRepo.findById(productId).orElseThrow(()-> new RuntimeException("Product not exits"));

        CartItem item = cartItemRepo.findByCartAndProduct(cart,product).orElseThrow(() -> new RuntimeException("item not found"));

        cartItemRepo.delete(item);
        updateCartTotals(cart);
        return true;
    }

    public void updateCartTotals(Cart cart){
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(CartItem item: cart.getCartItems()){
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        cart.setTotalPrice(totalPrice);
        cartRepo.save(cart);
    }
}
