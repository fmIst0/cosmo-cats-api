package com.example.cosmocatsapi.dto.order;

import com.example.cosmocatsapi.common.OrderStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderRequestDto {
    @NotNull(message = "Order items cannot be null")
    List<OrderItemDto> orderItems;
    @NotNull(message = "Address cannot be null")
    String address;
    @Email(message = "Email is not valid")
    String email;
    @NotNull(message = "Phone number cannot be null")
    String phone;
    @NotNull(message = "Total price cannot be null")
    BigDecimal totalPrice;
    @NotNull(message = "Status cannot be null")
    OrderStatus status;
}
