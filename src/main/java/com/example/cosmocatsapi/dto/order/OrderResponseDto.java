package com.example.cosmocatsapi.dto.order;

import com.example.cosmocatsapi.common.OrderStatus;
import com.example.cosmocatsapi.domain.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderResponseDto {
    String id;
    String name;
    String address;
    String phone;
    String email;
    OrderStatus status;
    BigDecimal totalPrice;
    List<OrderItem> orderItems;
}
