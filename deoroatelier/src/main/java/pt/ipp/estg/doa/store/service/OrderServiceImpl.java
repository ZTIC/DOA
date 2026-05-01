package pt.ipp.estg.doa.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ipp.estg.doa.store.exception.*;
import pt.ipp.estg.doa.store.model.dto.request.AddOrderItemRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateOrderRequest;
import pt.ipp.estg.doa.store.model.dto.request.OrderItemRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateOrderStatusRequest;
import pt.ipp.estg.doa.store.model.dto.response.OrderItemResponse;
import pt.ipp.estg.doa.store.model.dto.response.OrderResponse;
import pt.ipp.estg.doa.store.model.dto.response.OrderSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.*;
import pt.ipp.estg.doa.store.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final JewelryRepository jewelryRepository;
    private final SalespersonRepository salespersonRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CustomerRepository customerRepository,
                            JewelryRepository jewelryRepository,
                            SalespersonRepository salespersonRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.jewelryRepository = jewelryRepository;
        this.salespersonRepository = salespersonRepository;
    }

    // ============= CREATE OPERATIONS =============

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        // Validate customer
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id",
                        request.getCustomerId()));

        // Create order
        Order order;
        if (request.getSalespersonId() != null) {
            Salesperson salesperson = salespersonRepository.findById(request.getSalespersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Salesperson", "id",
                            request.getSalespersonId()));
            order = new Order(customer, salesperson);
        } else {
            order = new Order(customer);
        }

        // Validate and add items
        for (OrderItemRequest itemRequest : request.getItems()) {
            Jewelry jewelry = jewelryRepository.findById(itemRequest.getJewelryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id",
                            itemRequest.getJewelryId()));

            // Check stock availability
            if (!jewelry.hasSufficientStock(itemRequest.getQuantity())) {
                throw new InsufficientStockException(
                        jewelry.getName(),
                        jewelry.getId(),
                        jewelry.getStock(),
                        itemRequest.getQuantity()
                );
            }

            OrderItem orderItem = new OrderItem(jewelry, itemRequest.getQuantity());
            order.addItem(orderItem);
        }

        // Save order (cascade will save items)
        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    // ============= READ OPERATIONS =============

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderSummaryResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSummaryResponse> getOrdersByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "id", customerId);
        }

        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSummaryResponse> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(this::mapToOrderSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSummaryResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(this::mapToOrderSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSummaryResponse> getOrdersBySalesperson(Long salespersonId) {
        if (!salespersonRepository.existsById(salespersonId)) {
            throw new ResourceNotFoundException("Salesperson", "id", salespersonId);
        }

        return orderRepository.findBySalespersonEmployeeId(salespersonId).stream()
                .map(this::mapToOrderSummaryResponse)
                .collect(Collectors.toList());
    }

    // ============= UPDATE OPERATIONS =============

    @Override
    public OrderResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        OrderStatus newStatus = request.getNewStatus();

        // Validate status transition
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new InvalidStatusTransitionException(order.getStatus(), newStatus);
        }

        // Handle stock operations based on status change
        handleStockOnStatusChange(order, newStatus);

        // Update status
        order.updateStatus(newStatus);

        // If order is delivered, update salesperson commissions
        if (newStatus == OrderStatus.DELIVERED && order.getSalesperson() != null) {
            Salesperson salesperson = order.getSalesperson();
            salesperson.addSale(order.getTotalAmount());
            salespersonRepository.save(salesperson);
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse addItemToOrder(Long orderId, AddOrderItemRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // Check if order can be modified
        if (!order.getStatus().canModify()) {
            throw new OrderProcessingException(
                    String.valueOf(orderId),
                    "add item",
                    "Order cannot be modified in status: " + order.getStatus()
            );
        }

        // Validate jewelry
        Jewelry jewelry = jewelryRepository.findById(request.getJewelryId())
                .orElseThrow(() -> new ResourceNotFoundException("Jewelry", "id",
                        request.getJewelryId()));

        // Check stock availability
        if (!jewelry.hasSufficientStock(request.getQuantity())) {
            throw new InsufficientStockException(
                    jewelry.getName(),
                    jewelry.getId(),
                    jewelry.getStock(),
                    request.getQuantity()
            );
        }

        // Check if item already exists (update quantity instead)
        order.getItems().stream()
                .filter(item -> item.getJewelry().getId().equals(request.getJewelryId()))
                .findFirst()
                .ifPresent(item -> {
                    int newQuantity = item.getQuantity() + request.getQuantity();
                    item.updateQuantity(newQuantity);
                });

        // Create new item
        OrderItem newItem = new OrderItem(jewelry, request.getQuantity());
        order.addItem(newItem);

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse removeItemFromOrder(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (!order.getStatus().canModify()) {
            throw new OrderProcessingException(
                    String.valueOf(orderId),
                    "remove item",
                    "Order cannot be modified in status: " + order.getStatus()
            );
        }

        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", itemId));

        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new InvalidOperationException("remove item",
                    "Item does not belong to this order");
        }

        order.removeItem(item);
        orderItemRepository.delete(item);

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    @Override
    public OrderResponse updateItemQuantity(Long orderId, Long itemId, Integer newQuantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        if (!order.getStatus().canModify()) {
            throw new OrderProcessingException(
                    String.valueOf(orderId),
                    "update item quantity",
                    "Order cannot be modified in status: " + order.getStatus()
            );
        }

        OrderItem item = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", itemId));

        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new InvalidOperationException("update item",
                    "Item does not belong to this order");
        }

        Jewelry jewelry = item.getJewelry();
        int quantityDiff = newQuantity - item.getQuantity();

        if (quantityDiff > 0 && !jewelry.hasSufficientStock(quantityDiff)) {
            throw new InsufficientStockException(
                    jewelry.getName(),
                    jewelry.getId(),
                    jewelry.getStock(),
                    quantityDiff
            );
        }

        item.updateQuantity(newQuantity);

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    // ============= DELETE/CANCEL OPERATIONS =============

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new InvalidOperationException("cancel order",
                    "Delivered orders cannot be canceled");
        }

        // Restore stock if needed
        if (order.getStatus() == OrderStatus.ACCEPTED) {
            restoreStock(order);
        }

        order.updateStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOperationException("delete order",
                    "Only pending orders can be deleted");
        }

        orderRepository.delete(order);
    }

    // ============= BUSINESS OPERATIONS =============

    @Override
    public OrderResponse acceptOrder(Long id) {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setNewStatus(OrderStatus.ACCEPTED);
        return updateOrderStatus(id, request);
    }

    @Override
    public OrderResponse deliverOrder(Long id) {
        UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
        request.setNewStatus(OrderStatus.DELIVERED);
        return updateOrderStatus(id, request);
    }

    @Override
    public OrderResponse processPayment(Long orderId, BigDecimal amount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // This will be implemented when Payment module is created
        // For now, just return the order

        return mapToOrderResponse(order);
    }

    // ============= VALIDATION =============

    @Override
    public boolean canModifyOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return order.getStatus().canModify();
    }

    @Override
    public boolean hasSufficientStock(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        return order.getItems().stream()
                .allMatch(item -> item.getJewelry()
                        .hasSufficientStock(item.getQuantity()));
    }

    // ============= STATISTICS =============

    @Override
    public BigDecimal getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public BigDecimal getRevenueBetween(LocalDate startDate, LocalDate endDate) {
        return orderRepository.getRevenueBetween(startDate, endDate);
    }

    @Override
    public long getOrderCountByStatus(OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    @Override
    public List<Object[]> getOrderCountByStatus() {
        return orderRepository.getOrderCountByStatus();
    }

    @Override
    public List<Object[]> getTopCustomers(int limit) {
        return orderRepository.findTopCustomers().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    // ============= PRIVATE HELPER METHODS =============

    private void handleStockOnStatusChange(Order order, OrderStatus newStatus) {
        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.ACCEPTED) {
            // Reserve stock (decrease)
            for (OrderItem item : order.getItems()) {
                Jewelry jewelry = item.getJewelry();
                jewelry.decreaseStock(item.getQuantity());
                jewelryRepository.save(jewelry);
            }
        } else if (currentStatus == OrderStatus.ACCEPTED && newStatus == OrderStatus.CANCELED) {
            // Restore stock
            restoreStock(order);
        }
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Jewelry jewelry = item.getJewelry();
            jewelry.increaseStock(item.getQuantity());
            jewelryRepository.save(jewelry);
        }
    }

    // ============= MAPPING METHODS =============

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();

        // Basic info
        response.setId(order.getOrderId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setTotalPaid(order.getTotalPaid());
        response.setRemainingAmount(order.getRemainingAmount());
        response.setFullyPaid(order.isFullyPaid());

        // Customer info
        if (order.getCustomer() != null) {
            response.setCustomerId(order.getCustomer().getCustomerId());
            response.setCustomerName(order.getCustomer().getName());
            response.setCustomerNif(order.getCustomer().getNif());
        }

        // Salesperson info
        if (order.getSalesperson() != null) {
            response.setSalespersonId(order.getSalesperson().getEmployeeId());
            response.setSalespersonName(order.getSalesperson().getName());
        }

        // Items
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toList());
        response.setItems(itemResponses);
        response.setItemCount(itemResponses.size());
        response.setTotalQuantity(order.getTotalQuantity());

        // Payment info
        response.setPaymentCount(order.getPayments().size());

        // Dates
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        return response;
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getOrderItemId(),
                item.getJewelry().getId(),
                item.getJewelry().getName(),
                item.getJewelry().getJewelryType(),
                item.getQuantity(),
                item.getPriceAtPurchase(),
                item.getSubtotal()
        );
    }

    private OrderSummaryResponse mapToOrderSummaryResponse(Order order) {
        return new OrderSummaryResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCustomer().getName(),
                order.getItems().size()
        );
    }
}
