package pt.ipp.estg.doa.store.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalespersonResponse extends EmployeeResponse {
    private BigDecimal commissionRate;
    private BigDecimal totalSales;
    private BigDecimal estimatedCommission;

    public SalespersonResponse() {}

    public SalespersonResponse(Long id, String type, String name, String nif, String email, String phone, String address,  LocalDate hireDate, BigDecimal salary, BigDecimal commissionRate, BigDecimal totalSales, BigDecimal estimatedCommission) {
        super(id, "SALESPERSON", name, nif, email, phone, hireDate, salary, address);
        this.commissionRate = commissionRate;
        this.totalSales = totalSales;
        this.estimatedCommission = estimatedCommission;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getEstimatedCommission() {
        return estimatedCommission;
    }

    public void setEstimatedCommission(BigDecimal estimatedCommission) {
        this.estimatedCommission = estimatedCommission;
    }
}
