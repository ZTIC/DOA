package pt.ipp.estg.doa.store.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateSalespersonRequest.class, name = "SALESPERSON"),
        @JsonSubTypes.Type(value = CreateManagerRequest.class, name = "MANAGER")
})
public abstract class CreateEmployeeRequest {
    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must not exceed 100 characters.")
    private String name;

    @NotBlank(message = "NIF is required.")
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate hireDate;

    @NotNull(message = "Salary is required.")
    @Positive(message = "Salary must be positive.")
    private BigDecimal salary;

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
}
