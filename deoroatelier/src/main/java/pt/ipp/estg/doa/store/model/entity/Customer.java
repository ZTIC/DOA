package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customer_tb",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_nif", columnNames = "nif"),
                @UniqueConstraint(name = "uk_customer_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_customer_phone", columnNames = "phone")
        })
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "NIF is required")
    @Pattern(regexp = "\\d{9}", message = "NIF must be exactly 9 digits")
    @Column(nullable = false, unique = true, length = 9)
    private String nif;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{9}", message = "Phone must be exactly 9 digits")
    @Column(nullable = false, unique = true, length = 9)
    private String phone;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Column(nullable = false)
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Column(name = "total_purchases")
    private Integer totalPurchases;

    @Column(name = "total_spent", precision = 15, scale = 2)
    private BigDecimal totalSpent;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "notes", length = 1000)
    private String notes;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Version
    private Integer version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Construtores
    protected Customer() {}

    public Customer(String name, String nif, String email, String phone, String address) {
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = true;
        this.loyaltyPoints = 0;
        this.totalPurchases = 0;
        this.totalSpent = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getCustomerId() { return customerId; }
    public void setId(Long customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getNif() { return nif; }
    public void setNif(String nif) {
        this.nif = nif;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        this.city = city;
        this.updatedAt = LocalDateTime.now();
    }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.updatedAt = LocalDateTime.now();
    }

    public String getCountry() { return country; }
    public void setCountry(String country) {
        this.country = country;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) {
        this.active = active;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getTotalPurchases() { return totalPurchases; }
    public void setTotalPurchases(Integer totalPurchases) {
        this.totalPurchases = totalPurchases;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getTotalSpent() { return totalSpent; }
    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getLastPurchaseDate() { return lastPurchaseDate; }
    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
        this.updatedAt = LocalDateTime.now();
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Métodos de negócio

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
        updatePurchaseStatistics(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
        // Note: Não atualizamos estatísticas ao remover para manter histórico
    }

    private void updatePurchaseStatistics(Order order) {
        this.totalPurchases++;
        this.totalSpent = this.totalSpent.add(order.getTotalAmount());
        this.lastPurchaseDate = LocalDate.now();

        // Atualizar pontos de fidelidade (exemplo: 1 ponto por cada 10€)
        int pointsEarned = order.getTotalAmount().divideToIntegralValue(new BigDecimal("10")).intValue();
        this.loyaltyPoints += pointsEarned;
    }

    public void recordPurchase(Order order) {
        updatePurchaseStatistics(order);
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasActiveOrders() {
        return orders.stream()
                .anyMatch(order -> order.getStatus() != OrderStatus.DELIVERED &&
                        order.getStatus() != OrderStatus.CANCELED);
    }

    public int getActiveOrdersCount() {
        return (int) orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED &&
                        order.getStatus() != OrderStatus.CANCELED)
                .count();
    }

    public BigDecimal getAverageOrderValue() {
        if (totalPurchases == 0) return BigDecimal.ZERO;
        return totalSpent.divide(BigDecimal.valueOf(totalPurchases), 2, RoundingMode.HALF_UP);
    }

    public String getCustomerTier() {
        if (totalPurchases == 0) return "NEW";
        if (totalPurchases >= 20 || totalSpent.compareTo(new BigDecimal("5000")) >= 0) return "VIP";
        if (totalPurchases >= 10 || totalSpent.compareTo(new BigDecimal("2000")) >= 0) return "GOLD";
        if (totalPurchases >= 5 || totalSpent.compareTo(new BigDecimal("500")) >= 0) return "SILVER";
        return "BRONZE";
    }

    public void deactivate() {
        if (hasActiveOrders()) {
            throw new IllegalStateException("Cannot deactivate customer with active orders");
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isBirthday() {
        if (birthDate == null) return false;
        LocalDate today = LocalDate.now();
        return birthDate.getMonth() == today.getMonth() &&
                birthDate.getDayOfMonth() == today.getDayOfMonth();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return customerId != null && Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Customer{id=%d, name='%s', nif='%s', email='%s', tier='%s'}",
                customerId, name, nif, email, getCustomerTier());
    }
}
