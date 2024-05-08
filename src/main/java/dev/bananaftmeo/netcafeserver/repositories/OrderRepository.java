package dev.bananaftmeo.netcafeserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.bananaftmeo.netcafeserver.models.Order;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO(o.id, o.timeCreated, o.orderStatus) FROM Order o")
    List<OrderDTO> findAllOrders();

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO(o.id, o.timeCreated, o.orderStatus) FROM Order o WHERE o.id = :id")
    OrderDTO findOrderDTOById(Long id);

    @Query("SELECT new dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO(o.id, o.timeCreated, o.orderStatus) FROM Order o WHERE o.user.id = :userId")
    List<OrderDTO> findOrdersByUserId(Long userId);
}
