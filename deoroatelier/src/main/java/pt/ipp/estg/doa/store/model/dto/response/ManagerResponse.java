package pt.ipp.estg.doa.store.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ManagerResponse extends EmployeeResponse {
    private String department;
    private BigDecimal bonus;
    private BigDecimal annualCompensation;

    public ManagerResponse(Long id, String type, String name, String nif, String email, String phone, LocalDate hireDate, BigDecimal salary, String address, String department, BigDecimal bonus, BigDecimal annualCompensation) {
        super(id, "MANAGER", name, nif, email, phone, hireDate, salary, address);
        this.department = department;
        this.bonus = bonus;
        this.annualCompensation = annualCompensation;
    }
}
