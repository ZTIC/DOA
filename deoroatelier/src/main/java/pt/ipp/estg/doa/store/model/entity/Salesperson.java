package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salesperson_tb")
@DiscriminatorValue("SALESPERSON")
public class Salesperson extends Employee{

    @NotNull(message = "Commission rate is required")
    @DecimalMin(value = "0.0", message = "Commission rate must be at least 0")
    @DecimalMax(value = "100.0", message = "Commission rate must not exceed 100")
    @Column(name = "commission_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal commissionRate;

    @NotNull(message = "Total sales is required")
    @PositiveOrZero(message = "Total sales must be zero or posite")
    @Column(name = "total_sales", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalSales;

    protected Salesperson() {}

    public Salesperson(String name, String nif, String email, String phone, String address, LocalDate hireDate, BigDecimal salary, BigDecimal commissionRate) {
        super(name, nif, email, phone, address, hireDate, salary);
        this.commissionRate = commissionRate;
        this.totalSales = BigDecimal.ZERO;
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

    @Override
    public String getEmployeeType() {
        return "SALESPERSON";
    }

    public void addSale(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sale amount must be greater than 0");
        }
        this.totalSales = this.totalSales.add(amount);
    }

    public BigDecimal calculateCommission() {
        return totalSales.multiply(commissionRate.divide(new BigDecimal(100)));
    }

    @Override
    public String toString() {
        return String.format("Salesperson{id=%d, name='%s', commissionRate=%.2f, totalSales=%.2f}", getEmployeeId(), getName(), commissionRate, totalSales);
    }
}
