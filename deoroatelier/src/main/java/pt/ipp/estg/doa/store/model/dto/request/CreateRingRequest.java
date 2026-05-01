package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateRingRequest extends CreateJewelryRequest {
    @NotNull(message = "Size is required")
    @Min(value = 10, message = "Ring size must be at least 10")
    @Max(value = 30, message = "Ring size must not exceed 30")
    private Integer size;

    // Getters e Setters
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}
