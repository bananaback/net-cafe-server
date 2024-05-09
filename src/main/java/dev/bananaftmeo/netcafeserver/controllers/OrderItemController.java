package dev.bananaftmeo.netcafeserver.controllers;

import org.springframework.web.bind.annotation.RestController;

import dev.bananaftmeo.netcafeserver.exceptions.OrderItemCreationException;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO;
import dev.bananaftmeo.netcafeserver.models.requests.CreateOrderItemRequest;
import dev.bananaftmeo.netcafeserver.models.responses.ErrorResponse;
import dev.bananaftmeo.netcafeserver.models.responses.OrderItemsResponse;
import dev.bananaftmeo.netcafeserver.services.orderitemservices.OrderItemService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<?> createOrderItem(@Validated @RequestBody CreateOrderItemRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            orderItemService.createOrderItem(request);
            return ResponseEntity.ok()
                    .body("Order item for order has Id " + request.getOrderId() + " created successfully.");
        } catch (OrderItemCreationException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getErrorMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrderItems() {
        List<OrderItemDTO> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok().body(new OrderItemsResponse(orderItems));
    }

    @GetMapping("/{productId}/{orderId}")
    public ResponseEntity<?> getOrderItemById(@PathVariable Long productId, @PathVariable Long orderId) {
        try {
            OrderItemDTO orderItem = orderItemService.getOrderItemById(productId, orderId);
            return ResponseEntity.ok().body(orderItem);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order item with product id " + productId + " and order id " + orderId
                            + " does not exist."));
        }
    }

    @PutMapping("/{productId}/{orderId}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Long productId, @PathVariable Long orderId,
            @Validated @RequestBody CreateOrderItemRequest createOrderItemRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Validation error: " + bindingResult.getAllErrors()));
        }
        try {
            orderItemService.updateOrderItem(productId, orderId, createOrderItemRequest);
            return ResponseEntity.ok().body("Update order item successfully.");
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order item with product id " + productId + " and order id " + orderId
                            + " does not exist."));
        }
    }

    @DeleteMapping("/{productId}/{orderId}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long productId, @PathVariable Long orderId) {
        try {
            orderItemService.deleteOrderItem(productId, orderId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getAllOrderItemsOfOrder(@PathVariable Long orderId) {
        try {
            List<OrderItemDTO> orderItems = orderItemService.getAllOrderItemsOfOrder(orderId);
            return ResponseEntity.ok().body(new OrderItemsResponse(orderItems));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Order with ID " + orderId + " does not exist."));
        }
    }

}
