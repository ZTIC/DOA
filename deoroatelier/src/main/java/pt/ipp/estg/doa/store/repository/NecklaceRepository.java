package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Necklace;

import java.math.BigDecimal;
import java.util.List;

public interface NecklaceRepository extends JpaRepository<Necklace, Long> {
    List<Necklace> findByLengthGreaterThan(BigDecimal length);

    List<Necklace> findByLengthBetween(BigDecimal minLength, BigDecimal maxLength);

    @Query("""
            SELECT n
             FROM Necklace n
              WHERE n.length >= :minLength ORDER BY n.length
            """)
    List<Necklace> findLongNecklaces(@Param("minLength") BigDecimal minLength);
}
