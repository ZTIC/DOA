package pt.ipp.estg.doa.store.model.dto.response;

import pt.ipp.estg.doa.store.model.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentSummaryResponse {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentMethod paymentMethod;
    private boolean confirmed;
    private String transactionId;

    public PaymentSummaryResponse() {
    }

    public PaymentSummaryResponse(Long id, Long orderId, BigDecimal amount,
                                  LocalDate paymentDate, PaymentMethod paymentMethod,
                                  boolean confirmed, String transactionId) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.confirmed = confirmed;
        this.transactionId = transactionId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
