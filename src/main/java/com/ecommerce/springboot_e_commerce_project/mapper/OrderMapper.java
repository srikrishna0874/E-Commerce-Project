package com.ecommerce.springboot_e_commerce_project.mapper;

import com.ecommerce.springboot_e_commerce_project.dto.OrderResponseDTO;
import com.ecommerce.springboot_e_commerce_project.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;


    public OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        orderResponseDTO.setOrderId(order.getOrderId());

        orderResponseDTO.setItems(
                order.getOrderItems()
                        .stream()
                        .map(orderItemMapper::toDTO)
                        .toList()
        );

        orderResponseDTO.setTotalItems(order.getTotalItems());
        orderResponseDTO.setTotalPrice(order.getTotalAmount());

        return orderResponseDTO;

    }

}
