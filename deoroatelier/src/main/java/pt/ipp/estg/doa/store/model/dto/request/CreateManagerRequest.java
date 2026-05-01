package pt.ipp.estg.doa.store.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class CreateManagerRequest extends CreateEmployeeRequest {
    @NotBlank(message = "Department is required.")
    private String department;

    @NotNull(message = "Bonus is required.")
    @PositiveOrZero(message = "Bonus must be zero or positive.")
    private BigDecimal bonus;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }
}
