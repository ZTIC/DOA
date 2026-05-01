package pt.ipp.estg.doa.store.service;


import pt.ipp.estg.doa.store.model.dto.request.AddOrderItemRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateOrderRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateOrderStatusRequest;
import pt.ipp.estg.doa.store.model.dto.response.OrderResponse;
import pt.ipp.estg.doa.store.model.dto.response.OrderSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    // Create operations
    OrderResponse createOrder(CreateOrderRequest request);

    // Read operations
    OrderResponse getOrderById(Long id);
    List<OrderSummaryResponse> getAllOrders();
    List<OrderSummaryResponse> getOrdersByCustomer(Long customerId);
    List<OrderSummaryResponse> getOrdersByStatus(OrderStatus status);
    List<OrderSummaryResponse> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);
    List<OrderSummaryResponse> getOrdersBySalesperson(Long salespersonId);

    // Update operations
    OrderResponse updateOrderStatus(Long id, UpdateOrderStatusRequest request);
    OrderResponse addItemToOrder(Long orderId, AddOrderItemRequest request);
    OrderResponse removeItemFromOrder(Long orderId, Long itemId);
    OrderResponse updateItemQuantity(Long orderId, Long itemId, Integer newQuantity);

    // Delete/Cancel operations
    void cancelOrder(Long id);
    void deleteOrder(Long id); // Only for PENDING orders

    // Business operations
    OrderResponse acceptOrder(Long id);
    OrderResponse deliverOrder(Long id);
    OrderResponse processPayment(Long orderId, BigDecimal amount);

    // Validation
    boolean canModifyOrder(Long orderId);
    boolean hasSufficientStock(Long orderId);

    // Statistics
    BigDecimal getTotalRevenue();
    BigDecimal getRevenueBetween(LocalDate startDate, LocalDate endDate);
    long getOrderCountByStatus(OrderStatus status);
    List<Object[]> getOrderCountByStatus();
    List<Object[]> getTopCustomers(int limit);

}
