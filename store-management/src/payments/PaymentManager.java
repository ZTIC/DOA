package payments;

import orders.Order;
import orders.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentManager {

    private final Map<Integer, Payment> payments = new HashMap<>();
    private int nextId = 1;

    public Payment addPayment(Order order, double amount, PaymentMethod paymentMethod) {

        double alreadyPaid = getTotalPaid(order);

        if (alreadyPaid + amount > order.getTotal()) {
            throw new IllegalStateException("Payment amount exceeds total amount");
        }

        Payment payment = new Payment(nextId++, order, amount, paymentMethod);
        payments.put(payment.getPaymentId(), payment);

        return payment;
    }

    public List<Payment> findByOrderId(int orderId) {
        return payments.values()
                .stream()
                .filter(payment -> payment.getOrder()
                        .getOrderId() == orderId).toList();
    }

    public double getTotalPaid(Order order) {
        return payments.values()
                .stream()
                .filter(payment -> payment.getOrder()
                .getOrderId() == order.getOrderId())
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
}
