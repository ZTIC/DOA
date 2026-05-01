package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.NotNull;
import pt.ipp.estg.doa.store.model.entity.OrderStatus;

public class UpdateOrderStatusRequest {
    @NotNull(message = "New status is required")
    private OrderStatus newStatus;

    private String reason;

    // Getters e Setters
    public OrderStatus getNewStatus() { return newStatus; }
    public void setNewStatus(OrderStatus newStatus) { this.newStatus = newStatus; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
