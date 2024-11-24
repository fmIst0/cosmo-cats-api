package com.example.cosmocatsapi.service;

import com.example.cosmocatsapi.dto.order.OrderRequestDto;
import com.example.cosmocatsapi.dto.order.OrderResponseDto;
import java.util.List;

public interface OrderService {
    OrderResponseDto addOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrder(String orderId);
    List<OrderResponseDto> getAllOrders();
}
