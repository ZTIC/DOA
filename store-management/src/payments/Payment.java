package payments;

import orders.Order;
import utils.ValidationUtils;

import java.time.LocalDateTime;

public class Payment {
    private final int paymentId;
    private final Order order;
    private final double amount;
    private final LocalDateTime paymentDate;
    private PaymentMethod paymentMethod;

    public Payment(int paymentId, Order order, double amount, PaymentMethod paymentMethod) {
        ValidationUtils.requirePositive(amount, "Payment amount");

        this.paymentId = paymentId;
        this.order = order;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDateTime.now();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
