package pt.ipp.estg.doa.store.model.dto.response;

import java.math.BigDecimal;

public class CustomerSummaryResponse {
    private Long id;
    private String name;
    private String nif;
    private String email;
    private String phone;
    private String customerTier;
    private Integer totalPurchases;
    private BigDecimal totalSpent;
    private boolean active;

    public CustomerSummaryResponse() {}

    public CustomerSummaryResponse(Long id, String name, String nif, String email,
                                   String phone, String customerTier,
                                   Integer totalPurchases, BigDecimal totalSpent,
                                   boolean active) {
        this.id = id;
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.phone = phone;
        this.customerTier = customerTier;
        this.totalPurchases = totalPurchases;
        this.totalSpent = totalSpent;
        this.active = active;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCustomerTier() { return customerTier; }
    public void setCustomerTier(String customerTier) { this.customerTier = customerTier; }

    public Integer getTotalPurchases() { return totalPurchases; }
    public void setTotalPurchases(Integer totalPurchases) { this.totalPurchases = totalPurchases; }

    public BigDecimal getTotalSpent() { return totalSpent; }
    public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
