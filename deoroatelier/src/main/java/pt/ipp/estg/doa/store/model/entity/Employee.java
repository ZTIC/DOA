package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "employee_tb")
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank(message = "Name is required.")
    @Column(nullable = false)
    @Size(min = 2, message = "Name must be at least 2 chars long.")
    private String name;
    @Column(unique = true, length = 9)
    @Pattern(regexp = "\\d{9}", message = "NIF must be exactly 9 digits")
    private String nif;

    @Column(unique = true)
    @Email(message = "Invalid email")
    private String email;

    @Column(unique = true)
    @Pattern(regexp = "\\d{9,15}", message = "Phone must contain between 9 and 15 digits")
    private String phone;

    @Size(max = 100)
    private String address;

    @NotNull(message = "Hire date is required.")
    @PastOrPresent(message = "Hire date must be in the past or present.")
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @NotNull(message = "Salary is required.")
    @Positive(message = "Salary must be positive.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    protected Employee() {}

    public Employee(String name, String nif, String email, String phone, String address, LocalDate hireDate, BigDecimal salary) {
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * * Business methods
     * */

    public abstract String getEmployeeType();

    public void increaseSalary(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("percentage must be greater than 0.");
        }
        BigDecimal increase = salary.multiply(percentage.divide(new BigDecimal(100)));
        this.salary = increase.add(increase);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) && Objects.equals(nif, employee.nif) && Objects.equals(email, employee.email) && Objects.equals(phone, employee.phone) && Objects.equals(hireDate, employee.hireDate);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Employee{ employeeId=%d, name='%s', nif='%s', email ='%s', phone='$s', address='%s', hireDate='%s', salary=%.2f}", getEmployeeId(), getName(), getNif(), getEmail(), getPhone(), getAddress(), getSalary());
    }
}
