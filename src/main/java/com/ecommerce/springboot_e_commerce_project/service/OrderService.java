package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.dto.UpdateOrderStatusRequest;
import com.ecommerce.springboot_e_commerce_project.model.*;
import com.ecommerce.springboot_e_commerce_project.repo.CartItemRepository;
import com.ecommerce.springboot_e_commerce_project.repo.CartRepository;
import com.ecommerce.springboot_e_commerce_project.repo.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Transactional
    public Order placeOrder(int cartId) {
        //TODO: check whether cart is valid (cart is non-empty, status = ACTIVE).
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found!"));
        List<CartItem> cartItems = getCartItems(cart);

        //TODO: update stock for each product first.
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            product.setProductStockQuantity(product.getProductStockQuantity() - cartItem.getQuantity());
        }

        //TODO: create order from cart
        Order order = new Order();

        int totalQuantity = cartItems
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        order.setTotalItems(totalQuantity);
        order.setTotalAmount(cart.getTotalPrice());

        //TODO: create order item from cart item

        List<OrderItem> orderItemList = cartItems.stream().map((cartItem) -> {
            OrderItem orderItem = new OrderItem();

            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);

            return orderItem;
        }).toList();

        order.setOrderItems(orderItemList);

        //TODO: change order status to PLACED
        order.setOrderStatus(OrderStatus.PLACED);

        //TODO: save order
        orderRepository.save(order);

        //TODO: remove all cart items from the cart
        System.out.println("Deleting cart items...");
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);

        return order;
    }

    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is Empty!");
        }

        if (cart.getCartStatus() == CartStatus.ORDERED) {
            throw new RuntimeException("Cart is already placed!");
        }

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + cartItem.getProduct().getProductName());
            }
        }

        return cartItems;
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(int orderId, UpdateOrderStatusRequest orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));

        OrderStatus currentOrderStatus = order.getOrderStatus();
        OrderStatus newStatus = OrderStatus.valueOf(orderStatus.getStatus().toUpperCase());
        Boolean canUpdateStatus = validateNewStatus(currentOrderStatus, newStatus);
        if (!canUpdateStatus) throw new RuntimeException("Order status to be updated is invalid!");

        order.setOrderStatus(newStatus);
        return order;
    }

    private Boolean validateNewStatus(OrderStatus currentOrderStatus, OrderStatus newOrderStatus) {
        switch (currentOrderStatus) {
            case CREATED -> {
                return newOrderStatus == OrderStatus.PLACED;
            }
            case PLACED -> {
                return newOrderStatus == OrderStatus.SHIPPED || newOrderStatus == OrderStatus.CANCELLED;
            }
            case SHIPPED -> {
                return newOrderStatus == OrderStatus.DELIVERED;
            }
            case CANCELLED, DELIVERED -> {
                return false;
            }
        }
        return false;

    }

    @Transactional
    public void deleteOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found!"));

        orderRepository.delete(order);
    }

    public CartItemRepository getCartItemRepository() {
        return cartItemRepository;
    }

    public void setCartItemRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
}
