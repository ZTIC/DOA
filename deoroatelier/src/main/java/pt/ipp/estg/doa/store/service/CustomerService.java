package pt.ipp.estg.doa.store.service;

import pt.ipp.estg.doa.store.model.dto.request.CreateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.response.CustomerResponse;
import pt.ipp.estg.doa.store.model.dto.response.CustomerSummaryResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    // Create operations
    CustomerResponse createCustomer(CreateCustomerRequest request);

    // Read operations
    CustomerResponse getCustomerById(Long id);
    CustomerResponse getCustomerByNif(String nif);
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse getCustomerByPhone(String phone);
    List<CustomerSummaryResponse> getAllCustomers();
    List<CustomerSummaryResponse> searchCustomers(String name, String city, Boolean active);

    // Update operations
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);
    CustomerResponse activateCustomer(Long id);
    CustomerResponse deactivateCustomer(Long id);

    // Delete operations
    void deleteCustomer(Long id);

    // Business operations
    CustomerResponse addLoyaltyPoints(Long id, Integer points);
    CustomerResponse recordPurchase(Long id, BigDecimal amount);
    List<CustomerResponse> getTopCustomersByPurchases(int limit);
    List<CustomerResponse> getTopCustomersBySpent(int limit);
    List<CustomerResponse> getCustomersWithBirthdayToday();
    List<CustomerResponse> getVipCustomers();
    List<CustomerResponse> getNewCustomers();

    // Statistics
    long getActiveCustomersCount();
    Double getAverageCustomerSpent();
    BigDecimal getTotalCustomerSpent();
    long getCustomersCountByTier(String tier);

    // Validation
    boolean existsByNif(String nif);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
