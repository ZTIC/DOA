package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "payment_tb")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be positive")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "Payment method is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "payment_reference")
    private String paymentReference;

    @Column(name = "is_confirmed")
    private boolean confirmed;

    @Column(name = "confirmation_date")
    private LocalDateTime confirmationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "notes")
    private String notes;

    @Version
    private Integer version;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Construtores
    protected Payment() {}

    public Payment(Order order, BigDecimal amount, PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDate.now();
        this.confirmed = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (paymentMethod.requiresReference()) {
            generateTransactionId();
        }
    }

    // Getters e Setters
    public Long getPaymentIdId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        this.updatedAt = LocalDateTime.now();
    }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.updatedAt = LocalDateTime.now();
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
        if (confirmed) {
            this.confirmationDate = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getConfirmationDate() { return confirmationDate; }
    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Order getOrder() { return order; }
    public void setOrder(Order order) {
        this.order = order;
        this.updatedAt = LocalDateTime.now();
    }

    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Métodos de negócio

    private void generateTransactionId() {
        this.transactionId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void confirm() {
        if (confirmed) {
            throw new IllegalStateException("Payment is already confirmed");
        }
        this.confirmed = true;
        this.confirmationDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (confirmed) {
            throw new IllegalStateException("Cannot cancel a confirmed payment");
        }
        this.confirmed = false;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isElectronic() {
        return paymentMethod.isElectronic();
    }

    public String getFormattedAmount() {
        return String.format("%.2f €", amount);
    }

    public String getPaymentSummary() {
        return String.format("%s - %s - %s",
                paymentDate,
                paymentMethod.getDisplayName(),
                getFormattedAmount());
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
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return paymentId != null && Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Payment{id=%d, amount=%.2f, method=%s, confirmed=%s, transaction=%s}",
                paymentId, amount, paymentMethod, confirmed, transactionId);
    }
}
