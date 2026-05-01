package pt.ipp.estg.doa.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ipp.estg.doa.store.exception.DuplicateNifException;
import pt.ipp.estg.doa.store.exception.InvalidOperationException;
import pt.ipp.estg.doa.store.exception.ResourceNotFoundException;
import pt.ipp.estg.doa.store.model.dto.request.CreateManagerRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateSalespersonRequest;
import pt.ipp.estg.doa.store.model.dto.response.EmployeeResponse;
import pt.ipp.estg.doa.store.model.dto.response.ManagerResponse;
import pt.ipp.estg.doa.store.model.dto.response.SalespersonResponse;
import pt.ipp.estg.doa.store.model.entity.Employee;
import pt.ipp.estg.doa.store.model.entity.Manager;
import pt.ipp.estg.doa.store.model.entity.Salesperson;
import pt.ipp.estg.doa.store.repository.EmployeeRepository;
import pt.ipp.estg.doa.store.repository.ManagerRepository;
import pt.ipp.estg.doa.store.repository.SalespersonRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalespersonRepository salespersonRepository;
    private final ManagerRepository managerRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, SalespersonRepository salespersonRepository, ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.salespersonRepository = salespersonRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public SalespersonResponse createSalesperson(CreateSalespersonRequest request) {
        // Check for duplicate NIF
        if (employeeRepository.existsByNif(request.getNif())) {
            throw new DuplicateNifException("Salesperson", request.getNif());
        }

        // Create new salesperson
        Salesperson salesperson = new Salesperson(
                request.getName(),
                request.getNif(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getHireDate(),
                request.getSalary(),
                request.getCommissionRate()
        );

        Salesperson saved = salespersonRepository.save(salesperson);
        return mapToSalespersonResponse(saved);
    }


    @Override
    public ManagerResponse createManager(CreateManagerRequest request) {

        // Check for duplicate NIF
        if (employeeRepository.existsByNif(request.getNif())) {
            throw new DuplicateNifException("Manager", request.getNif());
        }

        // Create new manager
        Manager manager = new Manager(
                request.getName(),
                request.getNif(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getHireDate(),
                request.getSalary(),
                request.getDepartment(),
                request.getBonus()
        );

        Manager saved = managerRepository.save(manager);
        return mapToManagerResponse(saved);
    }

    @Override
    public List<SalespersonResponse> getAllSalespersons() {
        return salespersonRepository.findAll()
                .stream().map(this::mapToSalespersonResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerResponse> getAllManagers() {
        return managerRepository.findAll()
                .stream()
                .map(this::mapToManagerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return mapToEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeByNif(String nif) {
        Employee employee = employeeRepository.findByNif(nif).orElseThrow(() -> new ResourceNotFoundException("Employee", "NIF", nif));
        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse updateEmployeeSalary(Long id, BigDecimal newSalary) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        if (newSalary == null || newSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Update salary", "Salary must be positive.");
        }

        employee.setSalary(newSalary);
        Employee updated = employeeRepository.save(employee);

        return mapToEmployeeResponse(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        // Check if employee can be deleted (add business rules here)
        // For example: check if employee has any associated orders
        employeeRepository.delete(employee);

    }

    @Override
    public SalespersonResponse addSaleToSalesperson(Long salespersonId, BigDecimal amount) {
        Salesperson salesperson = salespersonRepository.findById(salespersonId).orElseThrow(() -> new ResourceNotFoundException("Salesperson", "id", salespersonId));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("add sale", "Sale amount must be positive");
        }

        salesperson.addSale(amount);
        Salesperson updated = salespersonRepository.save(salesperson);

        return mapToSalespersonResponse(updated);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByType(String type) {
        Class<?> employeeClass = getEmployeeClassFromType(type);
        return employeeRepository.findByEmployeeType(employeeClass)
                .stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    private Class<?> getEmployeeClassFromType(String type) {
        return switch (type.toUpperCase()) {
            case "SALESPERSON" -> Salesperson.class;
            case "MANAGER" -> Manager.class;
            default -> throw new InvalidOperationException("filter by type",
                    "Invalid employee type: " + type);
        };
    }

    @Override
    public boolean existsByNif(String nif) {
        return employeeRepository.existsByNif(nif);
    }

    /***
     * Mapping methods
     * */
    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        if (employee instanceof Salesperson) {
            return mapToSalespersonResponse((Salesperson) employee);
        } else if (employee instanceof Manager) {
            return mapToManagerResponse((Manager) employee);
        } else {
            throw new IllegalArgumentException("Unknown employee type: " + employee.getClass());
        }
    }

    private SalespersonResponse mapToSalespersonResponse(Salesperson salesperson) {
        return new SalespersonResponse(
                salesperson.getEmployeeId(),
                salesperson.getEmployeeType(),
                salesperson.getName(),
                salesperson.getNif(),
                salesperson.getEmail(),
                salesperson.getPhone(),
                salesperson.getAddress(),
                salesperson.getHireDate(),
                salesperson.getSalary(),
                salesperson.getCommissionRate(),
                salesperson.getTotalSales(),
                salesperson.calculateCommission()
        );
    }

    private ManagerResponse mapToManagerResponse(Manager manager) {
        return new ManagerResponse(
                manager.getEmployeeId(),
                manager.getEmployeeType(),
                manager.getName(),
                manager.getNif(),
                manager.getEmail(),
                manager.getPhone(),
                manager.getHireDate(),
                manager.getSalary(),
                manager.getAddress(),
                manager.getDepartment(),
                manager.getBonus(),
                manager.getAnnualCompensation()
        );
    }

}
