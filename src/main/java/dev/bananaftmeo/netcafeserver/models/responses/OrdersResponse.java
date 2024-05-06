package dev.bananaftmeo.netcafeserver.models.responses;

import java.util.List;

import dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponse {
    private List<OrderDTO> orders;
}
