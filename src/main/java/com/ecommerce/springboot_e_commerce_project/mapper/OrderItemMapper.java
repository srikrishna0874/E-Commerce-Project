package com.ecommerce.springboot_e_commerce_project.mapper;

import com.ecommerce.springboot_e_commerce_project.dto.OrderItemDTO;
import com.ecommerce.springboot_e_commerce_project.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemDTO toDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setProductId(orderItem.getProduct().getProductId());
        orderItemDTO.setProductName(orderItem.getProduct().getProductName());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setQuantity(orderItem.getQuantity());

        return orderItemDTO;
    }

}
