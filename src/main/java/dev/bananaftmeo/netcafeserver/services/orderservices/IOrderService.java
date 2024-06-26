package dev.bananaftmeo.netcafeserver.services.orderservices;

import java.util.List;

import org.springframework.security.core.Authentication;

import dev.bananaftmeo.netcafeserver.exceptions.OrderCreationException;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO;
import dev.bananaftmeo.netcafeserver.models.requests.UpdateOrderStatusRequest;

public interface IOrderService {
    void createOrder(Authentication authentication) throws OrderCreationException;

    OrderDTO createOrder(Long userId) throws OrderCreationException;

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getOrdersByUserId(Long userId);

    OrderDTO getOrderById(Long id);

    void updateOrderStatus(Long id, UpdateOrderStatusRequest request);

    void deleteOrder(Long id);
}
