package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String name);

    Optional<Employee> findByNif(String nif);

    Optional<Employee> findByEmail(String email);

    boolean existsByNif(String nif);

    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("""
            SELECT e
            FROM Employee e
            WHERE TYPE(e) = :type
            """)
    List<Employee> findByEmployeeType(@Param("type") Class<?> type);

    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.salary >= :minSalary
            """)
    List<Employee> findBySalaryGreaterThanEqual(@Param("minSalary") BigDecimal minSalary);
}
