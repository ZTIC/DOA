package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find by unique fields
    Optional<Customer> findByNif(String nif);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    // Check existence
    boolean existsByNif(String nif);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    // Find by name (search)
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Find by location
    List<Customer> findByCity(String city);

    List<Customer> findByCountry(String country);

    // Find by status
    List<Customer> findByActive(boolean active);

    // Find by loyalty points
    List<Customer> findByLoyaltyPointsGreaterThanEqual(Integer points);

    // Find by birthdate
    List<Customer> findByBirthDate(LocalDate birthDate);

    // Find customers with birthday today
    @Query("""
            SELECT c
             FROM Customer c
              WHERE FUNCTION('MONTH', c.birthDate) = :month AND FUNCTION('DAY', c.birthDate) = :day
            """)
    List<Customer> findCustomersWithBirthday(@Param("month") int month, @Param("day") int day);

    // Top customers by purchases
    @Query("""
            SELECT c
             FROM Customer c
              ORDER BY c.totalPurchases DESC
            """)
    List<Customer> findTopCustomersByPurchases();

    @Query("""
            SELECT c
             FROM Customer c ORDER BY c.totalSpent DESC
            """)
    List<Customer> findTopCustomersBySpent();

    // Find customers with no purchases
    @Query("""
            SELECT c
             FROM Customer c
              WHERE c.totalPurchases = 0
            """)
    List<Customer> findNewCustomers();

    // Find customers with recent purchases
    @Query("""
            SELECT c
             FROM Customer c
              WHERE c.lastPurchaseDate >= :date
            """)
    List<Customer> findCustomersWithRecentPurchases(@Param("date") LocalDate date);

    // Find VIP customers (based on tier logic)
    @Query("""
            SELECT c
             FROM Customer c
              WHERE c.totalPurchases >= 20 OR c.totalSpent >= 5000
            """)
    List<Customer> findVipCustomers();

    @Query("""
            SELECT c
             FROM Customer c
              WHERE c.totalPurchases BETWEEN 10 AND 19 OR c.totalSpent BETWEEN 2000 AND 4999.99
            """)
    List<Customer> findGoldCustomers();

    // Statistics
    @Query("""
            SELECT COUNT(c)
             FROM Customer c
              WHERE c.active = true
            """)
    long countActiveCustomers();

    @Query("""
            SELECT AVG(c.totalSpent)
             FROM Customer c
              WHERE c.totalPurchases > 0
            """)
    Double getAverageCustomerSpent();

    @Query("""
            SELECT SUM(c.totalSpent)
             FROM Customer c
            """)
    BigDecimal getTotalCustomerSpent();

    // Search with multiple criteria
    @Query("""
            SELECT c
             FROM Customer c
              WHERE (:name IS NULL OR LOWER(c.name)
               LIKE LOWER(CONCAT('%', :name, '%'))) AND (:city IS NULL OR LOWER(c.city)
               LIKE LOWER(CONCAT('%', :city, '%'))) AND (:active IS NULL OR c.active = :active)
            """)
    List<Customer> searchCustomers(@Param("name") String name,
                                   @Param("city") String city,
                                   @Param("active") Boolean active);
}
