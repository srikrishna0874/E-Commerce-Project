package com.ecommerce.springboot_e_commerce_project.mapper;

import com.ecommerce.springboot_e_commerce_project.dto.CartItemDTO;
import com.ecommerce.springboot_e_commerce_project.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemDTO toDTO(CartItem cartItem){
        CartItemDTO itemDTO = new CartItemDTO();

        itemDTO.setProductId(cartItem.getProduct().getProductId());
        itemDTO.setProductName(cartItem.getProduct().getProductName());
        itemDTO.setQuantity(cartItem.getQuantity());
        itemDTO.setPrice(cartItem.getPrice());

        return itemDTO;
    }
}
