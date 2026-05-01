package pt.ipp.estg.doa.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.estg.doa.store.model.dto.request.CreateManagerRequest;
import pt.ipp.estg.doa.store.model.dto.request.CreateSalespersonRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateSalaryRequest;
import pt.ipp.estg.doa.store.model.dto.response.EmployeeResponse;
import pt.ipp.estg.doa.store.model.dto.response.ManagerResponse;
import pt.ipp.estg.doa.store.model.dto.response.SalespersonResponse;
import pt.ipp.estg.doa.store.service.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
@Tag(name = "Colaboradores", description = "Gerenciador de colaboradores")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    // ============= SALESPERSON ENDPOINTS =============

    @PostMapping("/salesperson")
    @Operation(summary = "Adicionar colaborador", description = "Adiciona um novo colaborador para as vendas")
    public ResponseEntity<SalespersonResponse> createSalesperson(
            @RequestBody @Valid CreateSalespersonRequest request) {
        SalespersonResponse response = employeeService.createSalesperson(request);
        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping("/salespersons")
    @Operation(summary = "Lista de colaborador", description = "Lista de colaboradores de vendas")
    public ResponseEntity<List<SalespersonResponse>> getAllSalespersons() {
        List<SalespersonResponse> responses = employeeService.getAllSalespersons();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{id}/sales")
    @Operation(summary = "Contador de vendas por colaborador", description = "Por cada venda adicona ao respetivo colaborador")
    public ResponseEntity<SalespersonResponse> addSale(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        SalespersonResponse response = employeeService.addSaleToSalesperson(id, amount);
        return ResponseEntity.ok(response);
    }

    // ============= MANAGER ENDPOINTS =============

    @PostMapping("/manager")
    @Operation(summary = "Adicionar colaborador", description = "Adicionar um gestor")
    public ResponseEntity<ManagerResponse> createManager(
            @Valid @RequestBody CreateManagerRequest request) {
        ManagerResponse response = employeeService.createManager(request);
        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping("/managers")
    public ResponseEntity<List<ManagerResponse>> getAllManagers() {
        List<ManagerResponse> responses = employeeService.getAllManagers();
        return ResponseEntity.ok(responses);
    }

    // ============= COMMON EMPLOYEE ENDPOINTS =============

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> responses = employeeService.getAllEmployees();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nif/{nif}")
    public ResponseEntity<EmployeeResponse> getEmployeeByNif(@PathVariable String nif) {
        EmployeeResponse response = employeeService.getEmployeeByNif(nif);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByType(
            @PathVariable String type) {
        List<EmployeeResponse> responses = employeeService.getEmployeesByType(type);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/salary")
    public ResponseEntity<EmployeeResponse> updateSalary(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSalaryRequest request) {
        EmployeeResponse response = employeeService.updateEmployeeSalary(id, request.getSalary());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/nif/{nif}")
    public ResponseEntity<Boolean> checkNifExists(@PathVariable String nif) {
        boolean exists = employeeService.existsByNif(nif);
        return ResponseEntity.ok(exists);
    }
}
