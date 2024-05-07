package dev.bananaftmeo.netcafeserver.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderItemRequest {
    private Long productId;
    private Long orderId;
    private int quantity;
}
