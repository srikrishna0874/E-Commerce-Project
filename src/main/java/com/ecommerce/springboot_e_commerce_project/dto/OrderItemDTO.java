package com.ecommerce.springboot_e_commerce_project.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
