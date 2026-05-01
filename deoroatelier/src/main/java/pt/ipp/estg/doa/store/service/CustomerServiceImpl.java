package pt.ipp.estg.doa.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ipp.estg.doa.store.exception.*;
import pt.ipp.estg.doa.store.model.dto.request.CreateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.request.UpdateCustomerRequest;
import pt.ipp.estg.doa.store.model.dto.response.CustomerResponse;
import pt.ipp.estg.doa.store.model.dto.response.CustomerSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.Customer;
import pt.ipp.estg.doa.store.repository.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ============= CREATE OPERATIONS =============

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        // Check for duplicates
        if (customerRepository.existsByNif(request.getNif())) {
            throw new DuplicateNifException("Customer", request.getNif());
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Customer", request.getEmail());
        }

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateFieldException("Customer", "phone", request.getPhone());
        }

        // Create new customer
        Customer customer = new Customer(
                request.getName(),
                request.getNif(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress()
        );

        // Set optional fields
        customer.setCity(request.getCity());
        customer.setPostalCode(request.getPostalCode());
        customer.setCountry(request.getCountry());
        customer.setBirthDate(request.getBirthDate());
        customer.setNotes(request.getNotes());

        Customer saved = customerRepository.save(customer);
        return mapToCustomerResponse(saved);
    }

    // ============= READ OPERATIONS =============

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByNif(String nif) {
        Customer customer = customerRepository.findByNif(nif)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "nif", nif));
        return mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        return mapToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByPhone(String phone) {
        Customer customer = customerRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "phone", phone));
        return mapToCustomerResponse(customer);
    }

    @Override
    public List<CustomerSummaryResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToCustomerSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerSummaryResponse> searchCustomers(String name, String city, Boolean active) {
        return customerRepository.searchCustomers(name, city, active).stream()
                .map(this::mapToCustomerSummaryResponse)
                .collect(Collectors.toList());
    }

    // ============= UPDATE OPERATIONS =============

    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        // Check email uniqueness if changed
        if (!customer.getEmail().equals(request.getEmail()) &&
                customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Customer", request.getEmail());
        }

        // Check phone uniqueness if changed
        if (!customer.getPhone().equals(request.getPhone()) &&
                customerRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateFieldException("Customer", "phone", request.getPhone());
        }

        // Update fields
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setPostalCode(request.getPostalCode());
        customer.setCountry(request.getCountry());
        customer.setBirthDate(request.getBirthDate());
        customer.setNotes(request.getNotes());

        if (request.getActive() != null) {
            if (request.getActive()) {
                customer.activate();
            } else {
                customer.deactivate();
            }
        }

        Customer updated = customerRepository.save(customer);
        return mapToCustomerResponse(updated);
    }

    @Override
    public CustomerResponse activateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.activate();
        Customer updated = customerRepository.save(customer);

        return mapToCustomerResponse(updated);
    }

    @Override
    public CustomerResponse deactivateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        if (customer.hasActiveOrders()) {
            throw new InvalidOperationException("deactivate customer",
                    "Customer has active orders");
        }

        customer.deactivate();
        Customer updated = customerRepository.save(customer);

        return mapToCustomerResponse(updated);
    }

    // ============= DELETE OPERATIONS =============

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        if (!customer.getOrders().isEmpty()) {
            throw new InvalidOperationException("delete customer",
                    "Customer has existing orders");
        }

        customerRepository.delete(customer);
    }

    // ============= BUSINESS OPERATIONS =============

    @Override
    public CustomerResponse addLoyaltyPoints(Long id, Integer points) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        Customer updated = customerRepository.save(customer);

        return mapToCustomerResponse(updated);
    }

    @Override
    public CustomerResponse recordPurchase(Long id, BigDecimal amount) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.setTotalPurchases(customer.getTotalPurchases() + 1);
        customer.setTotalSpent(customer.getTotalSpent().add(amount));
        customer.setLastPurchaseDate(LocalDate.now());

        // Add loyalty points (1 point per 10€)
        int pointsEarned = amount.divideToIntegralValue(new BigDecimal("10")).intValue();
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + pointsEarned);

        Customer updated = customerRepository.save(customer);
        return mapToCustomerResponse(updated);
    }

    @Override
    public List<CustomerResponse> getTopCustomersByPurchases(int limit) {
        return customerRepository.findTopCustomersByPurchases().stream()
                .limit(limit)
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getTopCustomersBySpent(int limit) {
        return customerRepository.findTopCustomersBySpent().stream()
                .limit(limit)
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return customerRepository.findCustomersWithBirthday(
                        today.getMonthValue(), today.getDayOfMonth()
                ).stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getVipCustomers() {
        return customerRepository.findVipCustomers().stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getNewCustomers() {
        return customerRepository.findNewCustomers().stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    // ============= STATISTICS =============

    @Override
    public long getActiveCustomersCount() {
        return customerRepository.countActiveCustomers();
    }

    @Override
    public Double getAverageCustomerSpent() {
        return customerRepository.getAverageCustomerSpent();
    }

    @Override
    public BigDecimal getTotalCustomerSpent() {
        return customerRepository.getTotalCustomerSpent();
    }

    @Override
    public long getCustomersCountByTier(String tier) {
        return switch (tier.toUpperCase()) {
            case "VIP" -> customerRepository.findVipCustomers().size();
            case "GOLD" -> customerRepository.findGoldCustomers().size();
            default -> 0;
        };
    }

    // ============= VALIDATION =============

    @Override
    public boolean existsByNif(String nif) {
        return customerRepository.existsByNif(nif);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    // ============= MAPPING METHODS =============

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getCustomerId());
        response.setName(customer.getName());
        response.setNif(customer.getNif());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        response.setPostalCode(customer.getPostalCode());
        response.setCountry(customer.getCountry());
        response.setBirthDate(customer.getBirthDate());
        response.setActive(customer.isActive());
        response.setLoyaltyPoints(customer.getLoyaltyPoints());
        response.setTotalPurchases(customer.getTotalPurchases());
        response.setTotalSpent(customer.getTotalSpent());
        response.setLastPurchaseDate(customer.getLastPurchaseDate());
        response.setCustomerTier(customer.getCustomerTier());
        response.setAverageOrderValue(customer.getAverageOrderValue());
        response.setActiveOrdersCount(customer.getActiveOrdersCount());
        response.setBirthday(customer.isBirthday());
        response.setNotes(customer.getNotes());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }

    private CustomerSummaryResponse mapToCustomerSummaryResponse(Customer customer) {
        return new CustomerSummaryResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getNif(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerTier(),
                customer.getTotalPurchases(),
                customer.getTotalSpent(),
                customer.isActive()
        );
    }
}
