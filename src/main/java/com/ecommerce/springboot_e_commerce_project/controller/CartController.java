package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.dto.AddToCartRequest;
import com.ecommerce.springboot_e_commerce_project.dto.CartItemDTO;
import com.ecommerce.springboot_e_commerce_project.dto.CartResponseDTO;
import com.ecommerce.springboot_e_commerce_project.dto.UpdateCartRequest;
import com.ecommerce.springboot_e_commerce_project.model.CartItem;
import com.ecommerce.springboot_e_commerce_project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{cartId}/add")
    public ResponseEntity<String> addToCart(@PathVariable int cartId, @RequestBody AddToCartRequest addToCartRequest){

        int productId = addToCartRequest.getProductId();
        int quantity = addToCartRequest.getQuantity();

        cartService.addToCart(cartId,productId,quantity);

        return new ResponseEntity<>("Added to Cart", HttpStatus.OK);
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartResponseDTO> getCartItems(@PathVariable int cartId){
//        System.out.println("Inside Cert Controller");
        return cartService.getCartItems(cartId);
    }

    @PutMapping("/{cartId}/update/{productId}")
    public ResponseEntity<CartItemDTO> updateCart(@PathVariable int cartId, @PathVariable int productId, @RequestBody UpdateCartRequest request){
        int quantity = request.getQuantity();

        CartItemDTO itemDTO = cartService.updateCart(cartId,productId,quantity);

        return new ResponseEntity<>(itemDTO,HttpStatus.OK);
    }

    @DeleteMapping("{cartId}/delete/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable int cartId, @PathVariable int productId){
        boolean deleted = cartService.removeItem(cartId, productId);

        if(deleted) return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
