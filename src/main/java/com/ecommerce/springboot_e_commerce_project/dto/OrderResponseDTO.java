package com.ecommerce.springboot_e_commerce_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private int orderId;
    private int totalItems;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> items;

}
