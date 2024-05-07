package dev.bananaftmeo.netcafeserver.models.dtos;

import java.time.LocalDateTime;

import dev.bananaftmeo.netcafeserver.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime timeCreated;
    private OrderStatusEnum orderStatus;
}
