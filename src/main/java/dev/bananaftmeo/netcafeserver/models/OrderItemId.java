package dev.bananaftmeo.netcafeserver.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class OrderItemId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;
}
