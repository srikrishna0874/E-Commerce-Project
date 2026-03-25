package com.ecommerce.springboot_e_commerce_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO {

    private int cartId;
    private int totalItems;
    private BigDecimal totalPrice;
    private List<CartItemDTO> items;

}
