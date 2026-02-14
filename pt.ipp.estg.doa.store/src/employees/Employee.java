package employees;

import utils.Persistable;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Employee implements Persistable {
    private int employeeId;
    private String name;
    private final String nif;
    private String email;
    private String phone;
    private String address;
    private final LocalDate hireDate;
    private double salary;

    public Employee(int employeeId, String name, String nif, String email, String phone, String address, LocalDate hireDate, double salary) {

        ValidationUtils.validateId(employeeId);
        ValidationUtils.validateName(name);
        ValidationUtils.validateNif(nif);
        ValidationUtils.validateEmail(email);
        ValidationUtils.validatePhone(phone);
        ValidationUtils.validateHireDate(hireDate);
        ValidationUtils.validateSalary(salary);

        this.employeeId = employeeId;
        this.name = name.trim();
        this.nif = nif.trim();
        this.email = email.trim();
        this.phone = phone.trim();
        this.address = address;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public void setName(String name) {
        ValidationUtils.validateName(name);
        this.name = name.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getNif() {
        return nif;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    // ---------- Behavior ----------

    /**
     * Compute payment per month.
     * Implemented by subclass.
     */
    public abstract double calculatePay();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }


    public String getFullEmployeeData() {
        return getName() + " " + getEmail() + " " + getPhone() + " " + getAddress() + " " + getHireDate() + " " + getSalary();
    }

    @Override
    public String toString() {
        return getFullEmployeeData();
    }
}
