package com.ecommerce.springboot_e_commerce_project.mapper;

import com.ecommerce.springboot_e_commerce_project.dto.CartItemDTO;
import com.ecommerce.springboot_e_commerce_project.dto.CartResponseDTO;
import com.ecommerce.springboot_e_commerce_project.model.Cart;
import com.ecommerce.springboot_e_commerce_project.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartMapper {

    @Autowired
    CartItemMapper cartItemMapper;

    public CartResponseDTO toDTO(Cart cart){
        CartResponseDTO dto = new CartResponseDTO();

        List<CartItemDTO> dtoItems = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(CartItem item : cart.getCartItems()){
            dtoItems.add(cartItemMapper.toDTO(item));
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        dto.setCartId(cart.getCartId());
        dto.setItems(dtoItems);
        dto.setTotalPrice(totalPrice);
        dto.setTotalItems(cart.getCartItems().size());

        return dto;
    }
}
