package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Ring;

import java.util.List;

public interface RingRepository extends JpaRepository<Ring, Long> {
    List<RingRepository> findBySize(Integer size);

    List<RingRepository> findBySizeBetween(Integer minSize, Integer maxSize);

    @Query("""
            SELECT r
             FROM Ring r
              WHERE r.size = :size AND r.category = 'BRIDAL'
            """)
    List<RingRepository> findBridalRingsBySize(@Param("size") Integer size);

    @Query("""
            SELECT AVG(r.size)
             FROM Ring r
            """)
    Double getAverageRingSize();
}
