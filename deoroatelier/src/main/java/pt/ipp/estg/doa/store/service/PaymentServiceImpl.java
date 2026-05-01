package pt.ipp.estg.doa.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.ipp.estg.doa.store.exception.InvalidOperationException;
import pt.ipp.estg.doa.store.exception.InvalidPaymentException;
import pt.ipp.estg.doa.store.exception.PaymentExceedsOrderAmountException;
import pt.ipp.estg.doa.store.exception.ResourceNotFoundException;
import pt.ipp.estg.doa.store.model.dto.request.CreatePaymentRequest;
import pt.ipp.estg.doa.store.model.dto.response.PaymentResponse;
import pt.ipp.estg.doa.store.model.dto.response.PaymentSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.Order;
import pt.ipp.estg.doa.store.model.entity.Payment;
import pt.ipp.estg.doa.store.model.entity.PaymentMethod;
import pt.ipp.estg.doa.store.repository.OrderRepository;
import pt.ipp.estg.doa.store.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // ============= CREATE OPERATIONS =============

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        // Validate order exists
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id",
                        request.getOrderId()));

        // Validate payment amount
        validatePaymentAmount(order.getOrderId(), request.getAmount());

        // Create payment
        Payment payment = new Payment(order, request.getAmount(), request.getPaymentMethod());

        // Set optional fields
        if (request.getPaymentReference() != null) {
            payment.setPaymentReference(request.getPaymentReference());
        }

        if (request.getNotes() != null) {
            payment.setNotes(request.getNotes());
        }

        Payment savedPayment = paymentRepository.save(payment);

        return mapToPaymentResponse(savedPayment);
    }

    @Override
    public PaymentResponse createAndConfirmPayment(CreatePaymentRequest request) {
        PaymentResponse response = createPayment(request);
        return confirmPayment(response.getId());
    }

    // ============= READ OPERATIONS =============

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return mapToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "transactionId",
                        transactionId));
        return mapToPaymentResponse(payment);
    }

    @Override
    public List<PaymentSummaryResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getPaymentsByOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }

        return paymentRepository.findByOrderOrderId(orderId).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getPaymentsByCustomer(Long customerId) {
        return paymentRepository.findByCustomerId(customerId).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getPaymentsByMethod(PaymentMethod method) {
        return paymentRepository.findByPaymentMethod(method).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getPaymentsByDate(LocalDate date) {
        return paymentRepository.findByPaymentDate(date).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentSummaryResponse> getUnconfirmedPayments() {
        return paymentRepository.findByConfirmed(false).stream()
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    // ============= UPDATE OPERATIONS =============

    @Override
    public PaymentResponse confirmPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        if (payment.isConfirmed()) {
            throw new InvalidPaymentException("Payment is already confirmed");
        }

        payment.confirm();
        Payment updated = paymentRepository.save(payment);

        return mapToPaymentResponse(updated);
    }

    @Override
    public PaymentResponse updatePaymentReference(Long id, String reference) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        payment.setPaymentReference(reference);
        Payment updated = paymentRepository.save(payment);

        return mapToPaymentResponse(updated);
    }

    @Override
    public PaymentResponse addNotes(Long id, String notes) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        payment.setNotes(notes);
        Payment updated = paymentRepository.save(payment);

        return mapToPaymentResponse(updated);
    }

    // ============= DELETE/CANCEL OPERATIONS =============

    @Override
    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        if (payment.isConfirmed()) {
            throw new InvalidOperationException("cancel payment",
                    "Confirmed payments cannot be cancelled");
        }

        payment.cancel();
        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        if (payment.isConfirmed()) {
            throw new InvalidOperationException("delete payment",
                    "Confirmed payments cannot be deleted");
        }

        paymentRepository.delete(payment);
    }

    // ============= BUSINESS OPERATIONS =============

    @Override
    public BigDecimal getTotalPaidForOrder(Long orderId) {
        BigDecimal total = paymentRepository.getTotalConfirmedPaymentsForOrder(orderId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public boolean isOrderFullyPaid(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        BigDecimal totalPaid = getTotalPaidForOrder(orderId);
        return totalPaid.compareTo(order.getTotalAmount()) >= 0;
    }

    @Override
    public BigDecimal getRemainingAmount(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        BigDecimal totalPaid = getTotalPaidForOrder(orderId);
        return order.getTotalAmount().subtract(totalPaid);
    }

    @Override
    public List<PaymentResponse> getPaymentHistoryForOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order", "id", orderId);
        }

        return paymentRepository.findByOrderOrderId(orderId).stream()
                .map(this::mapToPaymentResponse)
                .collect(Collectors.toList());
    }

    // ============= STATISTICS =============

    @Override
    public BigDecimal getTotalPaymentsBetween(LocalDate startDate, LocalDate endDate) {
        BigDecimal total = paymentRepository.getTotalPaymentsBetween(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public List<Object[]> getPaymentMethodBreakdown(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.getPaymentMethodBreakdown(startDate, endDate);
    }

    @Override
    public List<Object[]> getDailyPaymentSummary(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.getDailyPaymentSummary(startDate, endDate);
    }

    @Override
    public List<PaymentSummaryResponse> getTopPayments(int limit) {
        return paymentRepository.findTop10ByOrderByAmountDesc().stream()
                .limit(limit)
                .map(this::mapToPaymentSummaryResponse)
                .collect(Collectors.toList());
    }

    // ============= VALIDATION =============

    @Override
    public void validatePaymentAmount(Long orderId, BigDecimal amount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        BigDecimal totalPaid = getTotalPaidForOrder(orderId);
        BigDecimal remaining = order.getTotalAmount().subtract(totalPaid);

        if (amount.compareTo(remaining) > 0) {
            throw new PaymentExceedsOrderAmountException(
                    orderId, order.getTotalAmount(), amount, totalPaid);
        }
    }

    // ============= MAPPING METHODS =============

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();

        // Basic payment info
        response.setId(payment.getPaymentIdId());
        response.setOrderId(payment.getOrder().getOrderId());
        response.setAmount(payment.getAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setTransactionId(payment.getTransactionId());
        response.setPaymentReference(payment.getPaymentReference());
        response.setConfirmed(payment.isConfirmed());
        response.setConfirmationDate(payment.getConfirmationDate());
        response.setNotes(payment.getNotes());
        response.setFormattedAmount(payment.getFormattedAmount());

        // Order info
        Order order = payment.getOrder();
        response.setCustomerName(order.getCustomer().getName());
        response.setOrderTotal(order.getTotalAmount());

        // Payment summary for the order
        BigDecimal totalPaid = getTotalPaidForOrder(order.getOrderId());
        response.setTotalPaidForOrder(totalPaid);
        response.setRemainingAmount(order.getTotalAmount().subtract(totalPaid));
        response.setOrderFullyPaid(totalPaid.compareTo(order.getTotalAmount()) >= 0);

        return response;
    }

    private PaymentSummaryResponse mapToPaymentSummaryResponse(Payment payment) {
        return new PaymentSummaryResponse(
                payment.getPaymentIdId(),
                payment.getOrder().getOrderId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.isConfirmed(),
                payment.getTransactionId()
        );
    }
}
