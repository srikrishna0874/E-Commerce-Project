package com.ecommerce.springboot_e_commerce_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {

    private int productId;
    private String productName;
    private BigDecimal price;
    private  int quantity;
}
