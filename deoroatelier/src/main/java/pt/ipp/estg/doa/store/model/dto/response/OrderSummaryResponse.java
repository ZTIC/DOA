package pt.ipp.estg.doa.store.model.dto.response;

import pt.ipp.estg.doa.store.model.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderSummaryResponse {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String customerName;
    private int itemCount;

    public OrderSummaryResponse() {}

    public OrderSummaryResponse(Long id, LocalDate orderDate, OrderStatus status,
                                BigDecimal totalAmount, String customerName, int itemCount) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
        this.itemCount = itemCount;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}
