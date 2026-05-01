package pt.ipp.estg.doa.store.exception;

import java.math.BigDecimal;

public class PaymentExceedsOrderAmountException extends RuntimeException {
    private final Long orderId;
    private final BigDecimal orderTotal;
    private final BigDecimal paymentAmount;
    private final BigDecimal totalPaid;
    private final BigDecimal remainingAmount;

    public PaymentExceedsOrderAmountException(Long orderId, BigDecimal orderTotal,
                                              BigDecimal paymentAmount, BigDecimal totalPaid) {
        super(String.format("Payment amount %.2f exceeds remaining order amount for order %d. Order total: %.2f, Already paid: %.2f, Remaining: %.2f",
                paymentAmount, orderId, orderTotal, totalPaid,
                orderTotal.subtract(totalPaid)));
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.paymentAmount = paymentAmount;
        this.totalPaid = totalPaid;
        this.remainingAmount = orderTotal.subtract(totalPaid);
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }
}
