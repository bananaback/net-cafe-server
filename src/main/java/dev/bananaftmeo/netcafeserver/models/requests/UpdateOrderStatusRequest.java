package dev.bananaftmeo.netcafeserver.models.requests;

import dev.bananaftmeo.netcafeserver.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    @NotNull(message = "Order status could not be null.")
    private OrderStatusEnum status;
}