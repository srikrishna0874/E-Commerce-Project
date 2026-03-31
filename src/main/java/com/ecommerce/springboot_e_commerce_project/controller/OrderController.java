package com.ecommerce.springboot_e_commerce_project.controller;


import com.ecommerce.springboot_e_commerce_project.common.ApiResponse;
import com.ecommerce.springboot_e_commerce_project.dto.OrderResponseDTO;
import com.ecommerce.springboot_e_commerce_project.dto.UpdateOrderStatusRequest;
import com.ecommerce.springboot_e_commerce_project.mapper.OrderMapper;
import com.ecommerce.springboot_e_commerce_project.model.Order;
import com.ecommerce.springboot_e_commerce_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/place/{cartId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> placeOrder(@PathVariable int cartId) {

        System.out.println("Placing order...");

        try {
            Order placedOrder = orderService.placeOrder(cartId);

            OrderResponseDTO orderResponseDTO = orderMapper.toDTO(placedOrder);
            ApiResponse<OrderResponseDTO> apiResponse = new ApiResponse<>(true, "Order Placed Successfully!", orderResponseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().
                    body(new ApiResponse<>(false, e.getMessage(), null));
        }

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable int orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            OrderResponseDTO orderResponseDTO = orderMapper.toDTO(order);

            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, "Order exists!", orderResponseDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().
                    body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {

        try {
            List<Order> allOrders = orderService.getAllOrders();
            List<OrderResponseDTO> orderResponseDTOs = allOrders.stream().map(orderMapper::toDTO).toList();

            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, "Orders fetched successfully!", orderResponseDTOs));

        } catch (Exception e) {
            return ResponseEntity.badRequest().
                    body(new ApiResponse<>(false, e.getMessage(), null));
        }


    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(@PathVariable int orderId, @RequestBody UpdateOrderStatusRequest orderStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);

            OrderResponseDTO orderResponseDTO = orderMapper.toDTO(updatedOrder);

            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, "Status updated Successfully!", orderResponseDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{orderId}")
    ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable int orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, "Order with id: " + orderId + " deleted successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new ApiResponse<>(false, e.getMessage(), null));
        }

    }

}
