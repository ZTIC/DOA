package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateEarringRequest extends CreateJewelryRequest {
    @NotBlank(message = "Clasp type is required")
    private String claspType;

    // Getters e Setters
    public String getClaspType() { return claspType; }
    public void setClaspType(String claspType) { this.claspType = claspType; }
}
