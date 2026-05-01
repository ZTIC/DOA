package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Order;
import pt.ipp.estg.doa.store.model.entity.Payment;
import pt.ipp.estg.doa.store.model.entity.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find by order
    List<Payment> findByOrder(Order order);

    List<Payment> findByOrderOrderId(Long orderId);

    // Find by payment method
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    // Find by date
    List<Payment> findByPaymentDate(LocalDate paymentDate);

    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<Payment> findByPaymentDateAfter(LocalDate date);

    List<Payment> findByPaymentDateBefore(LocalDate date);

    // Find by confirmation status
    List<Payment> findByConfirmed(boolean confirmed);

    List<Payment> findByConfirmedFalseAndPaymentDateBefore(LocalDate date);

    // Find by transaction/reference
    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByPaymentReference(String paymentReference);

    // Statistics for an order
    @Query("""
            SELECT SUM(p.amount)
             FROM Payment p
             WHERE p.order.orderId = :orderId AND p.confirmed = true
            """)
    BigDecimal getTotalConfirmedPaymentsForOrder(@Param("orderId") Long orderId);

    @Query("""
            SELECT COUNT(p)
             FROM Payment p
             WHERE p.order.orderId = :orderId
            """)
    int countPaymentsForOrder(@Param("orderId") Long orderId);

    // Statistics by period
    @Query("""
            SELECT SUM(p.amount)
             FROM Payment p
              WHERE p.paymentDate
               BETWEEN :startDate AND :endDate AND p.confirmed = true
            """)
    BigDecimal getTotalPaymentsBetween(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT p.paymentMethod, SUM(p.amount), COUNT(p)
                        FROM Payment p
                        WHERE p.paymentDate BETWEEN :startDate AND :endDate
                        AND p.confirmed = true
                        GROUP BY p.paymentMethod
            """)
    List<Object[]> getPaymentMethodBreakdown(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    // Daily statistics
    @Query("""
            SELECT p.paymentDate, SUM(p.amount), COUNT(p)
                        FROM Payment p
                        WHERE p.paymentDate BETWEEN :startDate AND :endDate
                        AND p.confirmed = true
                        GROUP BY p.paymentDate
                        ORDER BY p.paymentDate
            """)
    List<Object[]> getDailyPaymentSummary(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    // Top payments
    List<Payment> findTop10ByOrderByAmountDesc();

    // Unconfirmed payments older than
    @Query("""
            SELECT p
             FROM Payment p
              WHERE p.confirmed = false AND p.createdAt < :dateTime
            """)
    List<Payment> findUnconfirmedOlderThan(@Param("dateTime") LocalDateTime dateTime);

    // Customer payment history
    @Query("""
            SELECT p
            FROM Payment p
             WHERE p.order.customer.customerId = :customerId
              ORDER BY p.paymentDate DESC
            """)
    List<Payment> findByCustomerId(@Param("customerId") Long customerId);
}
