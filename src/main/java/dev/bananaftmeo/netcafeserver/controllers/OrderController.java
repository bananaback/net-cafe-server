package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.OrderCreationException;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderDTO;
import dev.bananaftmeo.netcafeserver.models.requests.UpdateOrderStatusRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.OrdersResponse;
import dev.bananaftmeo.netcafeserver.services.orderservices.OrderService;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(Authentication authentication) {
        try {
            orderService.createOrder(authentication);
            return ResponseEntity.ok().body("Order created successfully");
        } catch (OrderCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Order creation failed:" + ex.getErrorMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(new OrdersResponse(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            return ResponseEntity.ok().body(order);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Product category with id " + id + " does not exist."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id,
            @Validated @RequestBody UpdateOrderStatusRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            orderService.updateOrderStatus(id, request);
            return ResponseEntity.ok().body("Update order status successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order with id " + id + " does not exist."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order with id " + id + " does not exist."));
        }
    }
}
