package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateSalespersonRequest extends CreateEmployeeRequest{
    @NotNull(message = "Commission rate is required.")
    @DecimalMin(value = "0.0", message = "Commission rate must be at least 0")
    @DecimalMax(value = "100.0", message = "Commission rate must not exceed 100")
    private BigDecimal commissionRate;

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }
}
