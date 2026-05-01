package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("MANAGER")
@Table(name = "manager_tb")
public class Manager extends Employee {

    @NotBlank(message = "Department is required.")
    @Column(nullable = false)
    private String department;

    @NotNull(message = "Bonus is required.")
    @PositiveOrZero(message = "Bonus must be zero or positive.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal bonus;

    protected Manager() {
        super();
    }

    public Manager(String name, String nif, String email, String phone, String address, LocalDate hireDate, BigDecimal salary, String department, BigDecimal bonus) {
        super(name, nif, email, phone, address, hireDate, salary);
        this.department = department;
        this.bonus = bonus;
    }

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

    /***
     * Business methods
     * */

    @Override
    public String getEmployeeType() {
        return "MANAGER";
    }

    public BigDecimal getAnnualCompensation() {
        return getSalary().multiply(new BigDecimal(12)).add(getBonus());
    }

    @Override
    public String toString() {
        return String.format("Manager{id=%d, name='%s', department='%s', bonus=%.2f", getEmployeeId(), getName(), getDepartment(), getBonus());
    }

}
