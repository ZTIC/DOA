package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateStockRequest {
    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer stock;

    // Getters e Setters
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
