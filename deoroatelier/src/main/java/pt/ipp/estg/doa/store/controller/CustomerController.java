package pt.ipp.estg.doa.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.estg.doa.store.model.dto.request.CreateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.response.CustomerResponse;
import pt.ipp.estg.doa.store.model.dto.response.CustomerSummaryResponse;
import pt.ipp.estg.doa.store.service.CustomerServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
@Tag(name = "Clientes", description = "Gerenciador de clientes")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    // ============= CREATE ENDPOINTS =============

    @PostMapping
    @Operation(summary = "Adicionar clientes", description = "Adicionar um novo cliente")
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ============= READ ENDPOINTS =============

    @GetMapping
    @Operation(summary = "Lista com todos os clientes", description = "Vista de todos os clientes já registados na base de dados.")
    public ResponseEntity<List<CustomerSummaryResponse>> getAllCustomers() {
        List<CustomerSummaryResponse> responses = customerService.getAllCustomers();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca de cliente", description = "Busca de clientes pelo ID.")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nif/{nif}")
    @Operation(summary = "Busca de cliente", description = "Busca de clientes pelo NIF.")
    public ResponseEntity<CustomerResponse> getCustomerByNif(@PathVariable String nif) {
        CustomerResponse response = customerService.getCustomerByNif(nif);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Busca de cliente", description = "Busca de clientes por EMAIL.")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        CustomerResponse response = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/phone/{phone}")
    @Operation(summary = "Busca de cliente", description = "Busca de clientes pelo número de TELEFONE.")
    public ResponseEntity<CustomerResponse> getCustomerByPhone(@PathVariable String phone) {
        CustomerResponse response = customerService.getCustomerByPhone(phone);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Busca de cliente", description = "Busca de clientes pelo NOME, CIDADE ou DISPONIBILIDADE.")
    public ResponseEntity<List<CustomerSummaryResponse>> searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean active) {
        List<CustomerSummaryResponse> responses = customerService.searchCustomers(name, city, active);
        return ResponseEntity.ok(responses);
    }

    // ============= UPDATE ENDPOINTS =============

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do cliente", description = "Atualizar os dados do cliente.")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/activate")
    @Operation(summary = "Atualizar dados do cliente", description = "Activar a conta do cliente.")
    public ResponseEntity<CustomerResponse> activateCustomer(@PathVariable Long id) {
        CustomerResponse response = customerService.activateCustomer(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deactivate")
    @Operation(summary = "Atualizar dados do cliente", description = "Desactivar a conta do cliente.")
    public ResponseEntity<CustomerResponse> deactivateCustomer(@PathVariable Long id) {
        CustomerResponse response = customerService.deactivateCustomer(id);
        return ResponseEntity.ok(response);
    }

    // ============= BUSINESS OPERATION ENDPOINTS =============

    @PostMapping("/{id}/loyalty-points")
    @Operation(summary = "Atualizar dados do cliente", description = "Adicionar pontos de fidelidade.")
    public ResponseEntity<CustomerResponse> addLoyaltyPoints(
            @PathVariable Long id,
            @RequestParam Integer points) {
        CustomerResponse response = customerService.addLoyaltyPoints(id, points);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/record-purchase")
    @Operation(summary = "Registo de Compras", description = "Regista compras do cliente.")
    public ResponseEntity<CustomerResponse> recordPurchase(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        CustomerResponse response = customerService.recordPurchase(id, amount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top/by-purchases")
    @Operation(summary = "Registo de Compras", description = "Top de compras do cliente.")
    public ResponseEntity<List<CustomerResponse>> getTopCustomersByPurchases(
            @RequestParam(defaultValue = "10") int limit) {
        List<CustomerResponse> responses = customerService.getTopCustomersByPurchases(limit);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/top/by-spent")
    @Operation(summary = "Registo de Compras", description = "Top 10 de compras do cliente.")
    public ResponseEntity<List<CustomerResponse>> getTopCustomersBySpent(
            @RequestParam(defaultValue = "10") int limit) {
        List<CustomerResponse> responses = customerService.getTopCustomersBySpent(limit);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/birthday-today")
    @Operation(summary = "Obter clientes", description = "Obter uma lista de clientes aniversariantes.")
    public ResponseEntity<List<CustomerResponse>> getCustomersWithBirthdayToday() {
        List<CustomerResponse> responses = customerService.getCustomersWithBirthdayToday();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/vip")
    @Operation(summary = "Obter clientes", description = "Obter uma lista de clientes VIPs.")
    public ResponseEntity<List<CustomerResponse>> getVipCustomers() {
        List<CustomerResponse> responses = customerService.getVipCustomers();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/new")
    @Operation(summary = "Obter clientes", description = "Obter uma lista de novos clientes.")
    public ResponseEntity<List<CustomerResponse>> getNewCustomers() {
        List<CustomerResponse> responses = customerService.getNewCustomers();
        return ResponseEntity.ok(responses);
    }

    // ============= STATISTICS ENDPOINTS =============

    @GetMapping("/statistics/active-count")
    @Operation(summary = "Obter clientes", description = "Obter uma lista de novos clientes.")
    public ResponseEntity<Long> getActiveCustomersCount() {
        long count = customerService.getActiveCustomersCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/statistics/average-spent")
    public ResponseEntity<Double> getAverageCustomerSpent() {
        Double average = customerService.getAverageCustomerSpent();
        return ResponseEntity.ok(average);
    }

    @GetMapping("/statistics/total-spent")
    public ResponseEntity<BigDecimal> getTotalCustomerSpent() {
        BigDecimal total = customerService.getTotalCustomerSpent();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/statistics/tier/{tier}")
    public ResponseEntity<Long> getCustomersCountByTier(@PathVariable String tier) {
        long count = customerService.getCustomersCountByTier(tier);
        return ResponseEntity.ok(count);
    }

    // ============= VALIDATION ENDPOINTS =============

    @GetMapping("/exists/nif/{nif}")
    @Operation(summary = "Verificar cliente", description = "Verifica se o cliente existe através do NIF.")
    public ResponseEntity<Boolean> existsByNif(@PathVariable String nif) {
        boolean exists = customerService.existsByNif(nif);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Verificar cliente", description = "Verifica se o cliente existe através do email.")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = customerService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/phone/{phone}")
    @Operation(summary = "Verificar cliente", description = "Verifica se o cliente existe através do número de telefone.")
    public ResponseEntity<Boolean> existsByPhone(@PathVariable String phone) {
        boolean exists = customerService.existsByPhone(phone);
        return ResponseEntity.ok(exists);
    }

    // ============= DELETE ENDPOINTS =============

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remover um cliente.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
