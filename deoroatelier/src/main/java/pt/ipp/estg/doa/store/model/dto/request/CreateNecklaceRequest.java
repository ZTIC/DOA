package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateNecklaceRequest extends CreateJewelryRequest {
    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    private BigDecimal length;

    // Getters e Setters
    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }

}
