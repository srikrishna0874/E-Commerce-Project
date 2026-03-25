package com.ecommerce.springboot_e_commerce_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private int productId;
    private int quantity;
}
