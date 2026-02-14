package employees;

import utils.CSVUtil;
import utils.ValidationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeManager {
    private final Map<Integer, Employee> employees = new HashMap<>();
    private int nextId = 1;

    // ADD Employee --> Salesperson
    public Salesperson addSalesPerson(
            String name, String nif, String email, String phone, String address, LocalDate hireDate, double salary, double commissionRate
    ) {
        ensureUniqueNif(nif);

        Salesperson sp = new Salesperson(
                nextId++,
                name,
                nif,
                email,
                phone,
                address,
                hireDate,
                salary,
                commissionRate,
                0.00
        );
        employees.put(sp.getEmployeeId(), sp);
        return sp;
    }

    // Add Employee --> Manager
    public Manager addManager(
            String name,
            String nif,
            String email,
            String phone,
            String address,
            LocalDate hireDate,
            double salary,
            String department,
            double bonus) {

        ensureUniqueNif(nif);

        Manager m = new Manager(
                nextId++,
                name,
                nif,
                email,
                phone,
                address,
                hireDate,
                salary,
                department,
                bonus
        );
        employees.put(m.getEmployeeId(), m);
        return m;
    }

    // Find users by ID
    public Employee findById(int employeeId) {
        return employees.get(employeeId);
    }

    // Find User by NAME
    public List<Employee> findByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }
        String search = name.toLowerCase();
        return employees.values()
                .stream()
                .filter(
                        employee -> employee.getName()
                                .toLowerCase()
                                .contains(search))
                .collect(Collectors.toList());
    }

    // Show all employees
    public List<Employee> listAll() {
        return new ArrayList<>(employees.values());
    }

    // Show managers
    public List<Salesperson> listSalespeople() {
        return employees.values().stream()
                .filter(employee -> employee instanceof Salesperson)
                .map(employee -> (Salesperson) employee)
                .collect(Collectors.toList());
    }

    // Show managers
    public List<Manager> listManagers() {
        return employees.values().stream()
                .filter(employee -> employee instanceof Manager)
                .map(employee -> (Manager) employee)
                .collect(Collectors.toList());
    }

    // ---------- Update -------------------------
    public void updateSalary(int employeeId, double newSalary) {
        Employee employee = getExistingEmployee(employeeId);
        ValidationUtils.validateSalary(newSalary);
        employee.setSalary(newSalary);
    }

    public void updateCommissionRate(int employeeId, double newCommissionRate) {
        Employee employee = getExistingEmployee(employeeId);

        if (!(employee instanceof Salesperson sp)) {
            throw new IllegalArgumentException("Employee " + employee.getName() + " is not a Salesperson");
        }
        sp.setCommissionRate(newCommissionRate);
    }

    public void updateManagerBonus(int employeeId, double bonus) {
        Employee employee = getExistingEmployee(employeeId);
        if (!(employee instanceof Manager m)) {
            throw new IllegalArgumentException("Employee " + employee.getName() + " is not a Manager");
        }
        m.setBonus(bonus);
    }

    // Delete
    public void removeEmployeeById(int employeeId) {
        Employee employee = getExistingEmployee(employeeId);
        // TODO: validate if salesperson have got orders in association
        employees.remove(employee.getEmployeeId());
    }

    // Business
    public double calculateTotalPayroll() {
        return employees.values().stream()
                .mapToDouble(employee -> employee.getSalary())
                .sum();
    }

    // CSV
    public void load(String filename) {
        List<String> lines = CSVUtil.readCSV(filename);
        for (String line : lines) {
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            String name = parts[2];
            String nif = parts[3];
            String email = parts[4];
            String phone = parts[5];
            String address = parts[6];
            LocalDate hireDate = LocalDate.parse(parts[7]);
            double salary = Double.parseDouble(parts[8]);

            Employee employee;

            if ("SALESPERSON".equals(type)) {
                double commissionRate = Double.parseDouble(parts[9]);
                double totalSales = Double.parseDouble(parts[10]);

                employee = new Salesperson(
                        id,
                        name,
                        nif,
                        email,
                        phone,
                        address,
                        hireDate,
                        salary,
                        commissionRate,
                        totalSales
                );
            } else if ("MANAGER".equals(type)) {
                String department = parts[7];
                double bonus = Double.parseDouble(parts[8]);

                employee = new Manager(
                        id,
                        name,
                        nif,
                        email,
                        phone,
                        address,
                        hireDate,
                        salary,
                        department,
                        bonus
                );
            } else {
                throw new IllegalArgumentException("Employee type not supported");
            }
            employees.put(id, employee);
            nextId = Math.max(nextId, id + 1);
        }
    }

    public void save(String filename) {
        List<String> lines = employees.values()
                .stream()
                .map(Employee::toCSV)
                .toList();
        CSVUtil.writeCSV(filename, lines);
    }

    // Helpers
    private void ensureUniqueNif(String nif) {
        boolean exists = employees.values().stream()
                .anyMatch(e -> e.getNif().equals(nif));

        if (exists) {
            throw new IllegalArgumentException("NIF already exists.");
        }
    }

    private Employee getExistingEmployee(int id) {
        Employee e = employees.get(id);
        if (e == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        return e;
    }
}
