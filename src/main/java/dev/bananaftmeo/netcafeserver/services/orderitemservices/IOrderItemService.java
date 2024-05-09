package dev.bananaftmeo.netcafeserver.services.orderitemservices;

import java.util.List;

import dev.bananaftmeo.netcafeserver.exceptions.OrderItemCreationException;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO;
import dev.bananaftmeo.netcafeserver.models.requests.CreateOrderItemRequest;
import dev.bananaftmeo.netcafeserver.models.responses.OrderPrice;

public interface IOrderItemService {
    void createOrderItem(CreateOrderItemRequest createOrderItemRequest)
            throws OrderItemCreationException;

    List<OrderItemDTO> getAllOrderItems();

    OrderItemDTO getOrderItemById(Long productId, Long orderId);

    List<OrderItemDTO> getOrderItemByOrderId(Long orderId);

    void updateOrderItem(Long productId, Long orderId, CreateOrderItemRequest productCategory);

    void deleteOrderItem(Long productId, Long orderId);
}