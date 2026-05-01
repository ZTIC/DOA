package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByDepartment(String department);

    List<Manager> findByDepartmentContainingIgnoreCase(String department);

    @Query("SELECT m FROM Manager m WHERE m.bonus > :minBonus")
    List<Manager> findByBonusGreaterThan(@Param("minBonus") BigDecimal minBonus);
}
