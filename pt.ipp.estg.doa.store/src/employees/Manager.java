package employees;

import java.time.LocalDate;

public class Manager extends Employee {
    private String department;
    private double bonus; //Annual performance

    public Manager(int employeeId, String name, String nif, String email, String phone, String address, LocalDate hireDate, double salary, String department, double bonus) {
        super(employeeId, name, nif, email, phone, address, hireDate, salary);

        validateDepartment(department);
        validateBonus(bonus);

        this.department = department;
        this.bonus = bonus;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        validateDepartment(department);
        this.department = department.trim();
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    private void validateBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Bonus cannot be negative.");
        }
    }

    private void validateDepartment(String department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null.");
        }
    }

    @Override
    public double calculatePay() {
        return getSalary() + bonus;
    }

    @Override
    public String toCSV() {
        return String.format(
                "%d,MANAGER,%s,%s,%s,%.2f,%s,%.2f",
                getEmployeeId(),
                getName(),
                getNif(),
                getHireDate(),
                getSalary(),
                department,
                bonus
        );
    }

    @Override
    public void fromCSV(String csvLine) {

    }

    @Override
    public String toString() {
        return "";
    }
}
