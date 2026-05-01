package pt.ipp.estg.doa.store.service;

import pt.ipp.estg.doa.store.model.dto.request.CreateManagerRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateSalespersonRequest;
import pt.ipp.estg.doa.store.model.dto.response.EmployeeResponse;
import pt.ipp.estg.doa.store.model.dto.response.ManagerResponse;
import pt.ipp.estg.doa.store.model.dto.response.SalespersonResponse;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {

    // Salesperson operations
    SalespersonResponse createSalesperson(CreateSalespersonRequest request);
    List<SalespersonResponse> getAllSalespersons();

    // Manager operations
    ManagerResponse createManager(CreateManagerRequest request);
    List<ManagerResponse> getAllManagers();

    // Common operations
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse getEmployeeByNif(String nif);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse updateEmployeeSalary(Long id, BigDecimal newSalary);
    void deleteEmployee(Long id);

    // Business operations
    SalespersonResponse addSaleToSalesperson(Long salespersonId, BigDecimal amount);
    List<EmployeeResponse> getEmployeesByType(String type);
    boolean existsByNif(String nif);
}
