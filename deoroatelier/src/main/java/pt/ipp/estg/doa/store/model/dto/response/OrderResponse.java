package pt.ipp.estg.doa.store.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.ipp.estg.doa.store.model.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private Long id;
    private LocalDate orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private BigDecimal totalPaid;
    private BigDecimal remainingAmount;
    private boolean fullyPaid;

    // Customer info
    private Long customerId;
    private String customerName;
    private String customerNif;

    // Salesperson info (if assigned)
    private Long salespersonId;
    private String salespersonName;

    // Items
    private List<OrderItemResponse> items;
    private int itemCount;
    private long totalQuantity;

    // Payment summary
    private int paymentCount;

    // Dates
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private List<PaymentSummaryResponse> payments;

    public OrderResponse() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getTotalPaid() { return totalPaid; }
    public void setTotalPaid(BigDecimal totalPaid) { this.totalPaid = totalPaid; }

    public BigDecimal getRemainingAmount() { return remainingAmount; }
    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public boolean isFullyPaid() { return fullyPaid; }
    public void setFullyPaid(boolean fullyPaid) { this.fullyPaid = fullyPaid; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerNif() { return customerNif; }
    public void setCustomerNif(String customerNif) { this.customerNif = customerNif; }

    public Long getSalespersonId() { return salespersonId; }
    public void setSalespersonId(Long salespersonId) { this.salespersonId = salespersonId; }

    public String getSalespersonName() { return salespersonName; }
    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }

    public long getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(long totalQuantity) { this.totalQuantity = totalQuantity; }

    public int getPaymentCount() { return paymentCount; }
    public void setPaymentCount(int paymentCount) { this.paymentCount = paymentCount; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }
}
