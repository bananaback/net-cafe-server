package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.bananaftmeo.netcafeserver.models.OrderItem;
import dev.bananaftmeo.netcafeserver.models.OrderItemId;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
        @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO(oi.quantity, p.price, oi.product.id, oi.order.id) "
                        +
                        "FROM OrderItem oi " +
                        "JOIN oi.product p " +
                        "WHERE oi.product.id = :productId AND oi.order.id = :orderId")
        OrderItemDTO findOrderItemDTOByProductIdAndOrderId(Long productId, Long orderId);

        @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO(oi.quantity, p.price, oi.product.id, oi.order.id) "
                        +
                        "FROM OrderItem oi " +
                        "JOIN oi.product p")
        List<OrderItemDTO> findAllOrderItemDTOs();

        // Method to find all order items for a given order ID
        @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO(oi.quantity, p.price, oi.product.id, oi.order.id) "
                        + "FROM OrderItem oi "
                        + "JOIN oi.product p "
                        + "WHERE oi.order.id = :orderId")
        List<OrderItemDTO> findAllOrderItemDTOsByOrderId(Long orderId);
}
