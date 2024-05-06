package dev.bananaftmeo.netcafeserver.services.orderservices;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.enums.OrderStatusEnum;
import dev.bananaftmeo.netcafeserver.exceptions.OrderCreationException;
import dev.bananaftmeo.netcafeserver.models.ApplicationUser;
import dev.bananaftmeo.netcafeserver.models.Order;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO;
import dev.bananaftmeo.netcafeserver.models.requests.UpdateOrderStatusRequest;
import dev.bananaftmeo.netcafeserver.repositories.OrderRepository;
import dev.bananaftmeo.netcafeserver.repositories.UserRepository;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder(Authentication authentication) throws OrderCreationException {
        ApplicationUser existingUser = userRepository.findByUsername((String) authentication.getPrincipal());
        if (existingUser != null) {
            Order order = new Order();
            order.setUser(existingUser);
            order.setOrderStatus(OrderStatusEnum.PENDING);
            order.setTimeCreated(LocalDateTime.now());
            orderRepository.save(order);
        } else {
            throw new OrderCreationException(
                    "User with username" + authentication.getPrincipal() + " not found.");
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        OrderDTO orderDTO = orderRepository.findOrderDTOById(id);
        if (orderDTO == null) {
            throw new NoSuchElementException("Order with id" + id + " does not exist");
        }
        return orderRepository.findOrderDTOById(id);
    }

    @Override
    public void updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order currentOrder = orderRepository.findById(id).get();
        currentOrder.setOrderStatus(request.getStatus());
        orderRepository.save(currentOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id).get();
        orderRepository.delete(existingOrder);
    }

}