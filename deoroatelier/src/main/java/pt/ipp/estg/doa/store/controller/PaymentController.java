package pt.ipp.estg.doa.store.controller;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.estg.doa.store.model.dto.request.CreatePaymentRequest;
import pt.ipp.estg.doa.store.model.dto.response.PaymentResponse;
import pt.ipp.estg.doa.store.model.dto.response.PaymentSummaryResponse;
import pt.ipp.estg.doa.store.model.entity.PaymentMethod;
import pt.ipp.estg.doa.store.service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ============= CREATE ENDPOINTS =============

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody @Valid CreatePaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return new ResponseEntity<>(response, CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponse> createAndConfirmPayment(
            @Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse response = paymentService.createAndConfirmPayment(request);
        return new ResponseEntity<>(response, CREATED);
    }

    // ============= READ ENDPOINTS =============

    @GetMapping
    public ResponseEntity<List<PaymentSummaryResponse>> getAllPayments() {
        List<PaymentSummaryResponse> responses = paymentService.getAllPayments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentByTransactionId(
            @PathVariable String transactionId) {
        PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentSummaryResponse>> getPaymentsByOrder(
            @PathVariable Long orderId) {
        List<PaymentSummaryResponse> responses = paymentService.getPaymentsByOrder(orderId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentSummaryResponse>> getPaymentsByCustomer(
            @PathVariable Long customerId) {
        List<PaymentSummaryResponse> responses = paymentService.getPaymentsByCustomer(customerId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/method/{method}")
    public ResponseEntity<List<PaymentSummaryResponse>> getPaymentsByMethod(
            @PathVariable PaymentMethod method) {
        List<PaymentSummaryResponse> responses = paymentService.getPaymentsByMethod(method);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<PaymentSummaryResponse>> getPaymentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<PaymentSummaryResponse> responses = paymentService.getPaymentsByDate(date);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentSummaryResponse>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PaymentSummaryResponse> responses = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/unconfirmed")
    public ResponseEntity<List<PaymentSummaryResponse>> getUnconfirmedPayments() {
        List<PaymentSummaryResponse> responses = paymentService.getUnconfirmedPayments();
        return ResponseEntity.ok(responses);
    }

    // ============= UPDATE ENDPOINTS =============

    @PostMapping("/{id}/confirm")
    public ResponseEntity<PaymentResponse> confirmPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.confirmPayment(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/reference")
    public ResponseEntity<PaymentResponse> updatePaymentReference(
            @PathVariable Long id,
            @RequestParam String reference) {
        PaymentResponse response = paymentService.updatePaymentReference(id, reference);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/notes")
    public ResponseEntity<PaymentResponse> addNotes(
            @PathVariable Long id,
            @RequestParam String notes) {
        PaymentResponse response = paymentService.addNotes(id, notes);
        return ResponseEntity.ok(response);
    }

    // ============= BUSINESS OPERATION ENDPOINTS =============

    @GetMapping("/order/{orderId}/total-paid")
    public ResponseEntity<BigDecimal> getTotalPaidForOrder(@PathVariable Long orderId) {
        BigDecimal totalPaid = paymentService.getTotalPaidForOrder(orderId);
        return ResponseEntity.ok(totalPaid);
    }

    @GetMapping("/order/{orderId}/remaining")
    public ResponseEntity<BigDecimal> getRemainingAmount(@PathVariable Long orderId) {
        BigDecimal remaining = paymentService.getRemainingAmount(orderId);
        return ResponseEntity.ok(remaining);
    }

    @GetMapping("/order/{orderId}/is-fully-paid")
    public ResponseEntity<Boolean> isOrderFullyPaid(@PathVariable Long orderId) {
        boolean fullyPaid = paymentService.isOrderFullyPaid(orderId);
        return ResponseEntity.ok(fullyPaid);
    }

    @GetMapping("/order/{orderId}/history")
    public ResponseEntity<List<PaymentResponse>> getPaymentHistoryForOrder(
            @PathVariable Long orderId) {
        List<PaymentResponse> history = paymentService.getPaymentHistoryForOrder(orderId);
        return ResponseEntity.ok(history);
    }

    // ============= DELETE/CANCEL ENDPOINTS =============

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // ============= STATISTICS ENDPOINTS =============

    @GetMapping("/statistics/total")
    public ResponseEntity<BigDecimal> getTotalPaymentsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = paymentService.getTotalPaymentsBetween(startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/statistics/method-breakdown")
    public ResponseEntity<List<Object[]>> getPaymentMethodBreakdown(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Object[]> breakdown = paymentService.getPaymentMethodBreakdown(startDate, endDate);
        return ResponseEntity.ok(breakdown);
    }

    @GetMapping("/statistics/daily-summary")
    public ResponseEntity<List<Object[]>> getDailyPaymentSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Object[]> summary = paymentService.getDailyPaymentSummary(startDate, endDate);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/statistics/top")
    public ResponseEntity<List<PaymentSummaryResponse>> getTopPayments(
            @RequestParam(defaultValue = "10") int limit) {
        List<PaymentSummaryResponse> topPayments = paymentService.getTopPayments(limit);
        return ResponseEntity.ok(topPayments);
    }
}
