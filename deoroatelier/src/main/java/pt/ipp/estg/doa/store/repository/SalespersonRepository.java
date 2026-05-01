package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Employee;
import pt.ipp.estg.doa.store.model.entity.Salesperson;

import java.math.BigDecimal;
import java.util.List;

public interface SalespersonRepository extends JpaRepository<Salesperson, Long> {

    List<Salesperson> findByCommissionRateGreaterThan(BigDecimal rate);

    @Query("SELECT s FROM Salesperson s WHERE s.totalSales >= :minSales")
    List<Salesperson> findTopPerformers(@Param("minSales") BigDecimal minSales);

    @Query("SELECT s FROM Salesperson s ORDER BY s.totalSales DESC")
    List<Salesperson> findAllOrderByTotalSalesDesc();
}
