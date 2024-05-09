package dev.bananaftmeo.netcafeserver.services.orderitemservices;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bananaftmeo.netcafeserver.exceptions.OrderItemCreationException;
import dev.bananaftmeo.netcafeserver.models.OrderItem;
import dev.bananaftmeo.netcafeserver.models.OrderItemId;
import dev.bananaftmeo.netcafeserver.models.Product;
import dev.bananaftmeo.netcafeserver.models.Order;
import dev.bananaftmeo.netcafeserver.models.dtos.OrderItemDTO;
import dev.bananaftmeo.netcafeserver.models.requests.CreateOrderItemRequest;
import dev.bananaftmeo.netcafeserver.repositories.OrderItemRepository;
import dev.bananaftmeo.netcafeserver.repositories.OrderRepository;
import dev.bananaftmeo.netcafeserver.repositories.ProductRepository;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrderItem(CreateOrderItemRequest createOrderItemRequest) throws OrderItemCreationException {
        try {
            // Retrieve product and order entities
            Product product = productRepository.findById(createOrderItemRequest.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));
            Order order = orderRepository.findById(createOrderItemRequest.getOrderId())
                    .orElseThrow(() -> new NoSuchElementException("Order not found"));

            // Check if the order item already exists
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(createOrderItemRequest.getOrderId());
            orderItemId.setProductId(createOrderItemRequest.getProductId());
            OrderItem existingOrderItem = orderItemRepository.findById(orderItemId)
                    .orElse(null);

            // If the order item already exists, throw an exception
            if (existingOrderItem != null) {
                throw new OrderItemCreationException(
                        "Order item with product id " + createOrderItemRequest.getProductId()
                                + " and order id " + createOrderItemRequest.getOrderId() + " already exists.");
            }

            // Create a new OrderItem instance
            OrderItem orderItem = new OrderItem();
            orderItem.setId(orderItemId); // Set the composite primary key
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(createOrderItemRequest.getQuantity());
            orderItem.setSinglePrice(product.getPrice());

            // Save the order item
            orderItemRepository.save(orderItem);
        } catch (NoSuchElementException ex) {
            throw new OrderItemCreationException("Product with product id " + createOrderItemRequest.getProductId()
                    + " or order with order id " + createOrderItemRequest.getOrderId() + " not found.");
        }
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAllOrderItemDTOs();
    }

    @Override
    public List<OrderItemDTO> getAllOrderItemsOfOrder(Long orderId) {
        return orderItemRepository.findAllOrderItemDTOsByOrderId(orderId);
    }

    @Override
    public OrderItemDTO getOrderItemById(Long productId, Long orderId) {
        OrderItemDTO orderItemDTO = orderItemRepository.findOrderItemDTOByProductIdAndOrderId(productId, orderId);
        if (orderItemDTO == null) {
            throw new NoSuchElementException(
                    "Order item with product id " + productId + " and order id " + orderId + " not found");
        }
        return orderItemDTO;
    }

    @Override
    public void updateOrderItem(Long productId, Long orderId, CreateOrderItemRequest updateRequest) {
        try {
            // Retrieve the existing order item
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(orderId);
            orderItemId.setProductId(productId);
            OrderItem existingOrderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new NoSuchElementException("Order item not found"));

            // Update the order item details
            existingOrderItem.setQuantity(updateRequest.getQuantity());

            // Save the updated order item
            orderItemRepository.save(existingOrderItem);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(
                    "Order item with product id " + productId + " and order id " + orderId + " not found");
        }
    }

    @Override
    public void deleteOrderItem(Long productId, Long orderId) {
        try {
            // Retrieve the existing order item
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrderId(orderId);
            orderItemId.setProductId(productId);
            OrderItem existingOrderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new NoSuchElementException("Order item not found"));

            // Delete the order item
            orderItemRepository.delete(existingOrderItem);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException(
                    "Order item with product id " + productId + " and order id " + orderId + " not found");
        }
    }

}
