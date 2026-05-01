package pt.ipp.estg.doa.store.service;

import pt.ipp.estg.doa.store.model.dto.request.CreatePaymentRequest;
import pt.ipp.estg.doa.store.model.dto.response.PaymentResponse;
import pt.ipp.estg.doa.store.model.dto.response.PaymentSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    // Create operations
    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse createAndConfirmPayment(CreatePaymentRequest request);

    // Read operations
    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByTransactionId(String transactionId);

    List<PaymentSummaryResponse> getAllPayments();

    List<PaymentSummaryResponse> getPaymentsByOrder(Long orderId);

    List<PaymentSummaryResponse> getPaymentsByCustomer(Long customerId);

    List<PaymentSummaryResponse> getPaymentsByMethod(PaymentMethod method);

    List<PaymentSummaryResponse> getPaymentsByDate(LocalDate date);

    List<PaymentSummaryResponse> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate);

    List<PaymentSummaryResponse> getUnconfirmedPayments();

    // Update operations
    PaymentResponse confirmPayment(Long id);

    PaymentResponse updatePaymentReference(Long id, String reference);

    PaymentResponse addNotes(Long id, String notes);

    // Delete/Cancel operations
    void cancelPayment(Long id);

    void deletePayment(Long id); // Only for unconfirmed payments

    // Business operations
    BigDecimal getTotalPaidForOrder(Long orderId);

    boolean isOrderFullyPaid(Long orderId);

    BigDecimal getRemainingAmount(Long orderId);

    List<PaymentResponse> getPaymentHistoryForOrder(Long orderId);

    // Statistics
    BigDecimal getTotalPaymentsBetween(LocalDate startDate, LocalDate endDate);

    List<Object[]> getPaymentMethodBreakdown(LocalDate startDate, LocalDate endDate);

    List<Object[]> getDailyPaymentSummary(LocalDate startDate, LocalDate endDate);

    List<PaymentSummaryResponse> getTopPayments(int limit);

    // Validation
    void validatePaymentAmount(Long orderId, BigDecimal amount);
}
