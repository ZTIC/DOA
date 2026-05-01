package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private Long salespersonId;

    @Valid
    @NotNull(message = "Order items are required")
    @Size(min = 1, message = "Order must have at least one item")
    private List<OrderItemRequest> items = new ArrayList<>();

    // Getters e Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getSalespersonId() { return salespersonId; }
    public void setSalespersonId(Long salespersonId) { this.salespersonId = salespersonId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
