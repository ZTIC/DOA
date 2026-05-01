package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Customer;
import pt.ipp.estg.doa.store.model.entity.Order;
import pt.ipp.estg.doa.store.model.entity.OrderStatus;
import pt.ipp.estg.doa.store.model.entity.Salesperson;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find by customer
    List<Order> findByCustomer(Customer customer);

    List<Order> findByCustomerId(Long customerId);

    // Find by status
    List<Order> findByStatus(OrderStatus status);

    // Find by date range
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<Order> findByOrderDateAfter(LocalDate date);

    List<Order> findByOrderDateBefore(LocalDate date);

    // Find by salesperson
    List<Order> findBySalesperson(Salesperson salesperson);

    List<Order> findBySalespersonEmployeeId(Long employeeId);

    // Find by customer and status
    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    // Complex queries
    @Query("""
            SELECT o
            FROM Order o
             WHERE o.totalAmount >= :minAmount
            """)
    List<Order> findByMinAmount(@Param("minAmount") BigDecimal minAmount);

    @Query("""
            SELECT o
             FROM Order o
              WHERE o.totalAmount <= :maxAmount
            """)
    List<Order> findByMaxAmount(@Param("maxAmount") BigDecimal maxAmount);

    @Query("""
            SELECT o
             FROM Order o
              WHERE o.totalAmount
              BETWEEN :minAmount AND :maxAmount""")
    List<Order> findByAmountRange(@Param("minAmount") BigDecimal minAmount,
                                  @Param("maxAmount") BigDecimal maxAmount);

    // Statistics
    @Query("""
            SELECT COUNT(o) FROM Order o WHERE o.status = :status""")
    long countByStatus(@Param("status") OrderStatus status);

    @Query("""
            SELECT SUM(o.totalAmount)
             FROM Order o
              WHERE o.status = 'DELIVERED'
            """)
    BigDecimal getTotalRevenue();

    @Query("""
            SELECT SUM(o.totalAmount)
             FROM Order o
              WHERE o.orderDate
               BETWEEN :start AND :end
            """)
    BigDecimal getRevenueBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
            SELECT o.status, COUNT(o)
             FROM Order o
              GROUP BY o.status""")
    List<Object[]> getOrderCountByStatus();

    @Query("""
            SELECT o.customer.customerId, COUNT(o) as orderCount
            FROM Order o
             GROUP BY o.customer.customerId
              ORDER BY orderCount DESC
            """)
    List<Object[]> findTopCustomers();

    // Recent orders
    List<Order> findTop10ByOrderByOrderDateDesc();

    @Query("""
            SELECT o
             FROM Order o
              WHERE o.status = 'PENDING' AND o.orderDate < :date
            """)
    List<Order> findPendingOrdersOlderThan(@Param("date") LocalDate date);
}
