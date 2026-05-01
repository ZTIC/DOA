package pt.ipp.estg.doa.store.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.estg.doa.store.model.dto.request.AddOrderItemRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateOrderRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateOrderStatusRequest;
import pt.ipp.estg.doa.store.model.dto.response.OrderResponse;
import pt.ipp.estg.doa.store.model.dto.response.OrderSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.OrderStatus;
import pt.ipp.estg.doa.store.service.OrderServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // ============= CREATE ENDPOINTS =============

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ============= READ ENDPOINTS =============

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getAllOrders() {
        List<OrderSummaryResponse> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersByCustomer(
            @PathVariable Long customerId) {
        List<OrderSummaryResponse> responses = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/salesperson/{salespersonId}")
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersBySalesperson(
            @PathVariable Long salespersonId) {
        List<OrderSummaryResponse> responses = orderService.getOrdersBySalesperson(salespersonId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersByStatus(
            @PathVariable OrderStatus status) {
        List<OrderSummaryResponse> responses = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OrderSummaryResponse> responses = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    // ============= UPDATE ENDPOINTS =============

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderResponse response = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<OrderResponse> addItemToOrder(
            @PathVariable Long id,
            @Valid @RequestBody AddOrderItemRequest request) {
        OrderResponse response = orderService.addItemToOrder(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderResponse> removeItemFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        OrderResponse response = orderService.removeItemFromOrder(orderId, itemId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderResponse> updateItemQuantity(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        OrderResponse response = orderService.updateItemQuantity(orderId, itemId, quantity);
        return ResponseEntity.ok(response);
    }

    // ============= BUSINESS OPERATION ENDPOINTS =============

    @PostMapping("/{id}/accept")
    public ResponseEntity<OrderResponse> acceptOrder(@PathVariable Long id) {
        OrderResponse response = orderService.acceptOrder(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<OrderResponse> deliverOrder(@PathVariable Long id) {
        OrderResponse response = orderService.deliverOrder(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ============= DELETE ENDPOINTS =============

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // ============= VALIDATION ENDPOINTS =============

    @GetMapping("/{id}/can-modify")
    public ResponseEntity<Boolean> canModifyOrder(@PathVariable Long id) {
        boolean canModify = orderService.canModifyOrder(id);
        return ResponseEntity.ok(canModify);
    }

    @GetMapping("/{id}/check-stock")
    public ResponseEntity<Boolean> hasSufficientStock(@PathVariable Long id) {
        boolean hasStock = orderService.hasSufficientStock(id);
        return ResponseEntity.ok(hasStock);
    }

    // ============= STATISTICS ENDPOINTS =============

    @GetMapping("/statistics/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        BigDecimal revenue = orderService.getTotalRevenue();
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/statistics/revenue-by-period")
    public ResponseEntity<BigDecimal> getRevenueBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal revenue = orderService.getRevenueBetween(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/statistics/count-by-status")
    public ResponseEntity<List<Object[]>> getOrderCountByStatus() {
        List<Object[]> counts = orderService.getOrderCountByStatus();
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/statistics/top-customers")
    public ResponseEntity<List<Object[]>> getTopCustomers(
            @RequestParam(defaultValue = "10") int limit) {
        List<Object[]> topCustomers = orderService.getTopCustomers(limit);
        return ResponseEntity.ok(topCustomers);
    }
}
