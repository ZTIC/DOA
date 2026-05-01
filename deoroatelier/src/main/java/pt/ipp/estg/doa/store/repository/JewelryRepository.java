package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.Jewelry;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;
import java.util.List;

public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findByType(JewelryType type);

    List<Jewelry> findByCategory(Category category);

    @Query("""
            SELECT j
             FROM Jewelry j
              WHERE j.stock > 0
            """)
    List<Jewelry> findInStock();

    @Query("""
            SELECT j
            FROM Jewelry j
             WHERE j.stock = 0
            """)
    List<Jewelry> findOutOfStock();

    List<Jewelry> findByMaterialContainingIgnoreCase(String material);

    @Query("""
            SELECT j
             FROM Jewelry j 
             WHERE j.price 
             BETWEEN :minPrice AND :maxPrice
            """)
    List<Jewelry> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice);

    @Query("""
            SELECT j
             FROM Jewelry j
              WHERE j.weight <= :maxWeight
            """)
    List<Jewelry> findByMaxWeight(@Param("maxWeight") BigDecimal maxWeight);

    @Query("""
            SELECT j
             FROM Jewelry j
              WHERE TYPE(j) = :type
            """)
    List<Jewelry> findByJewelryType(@Param("type") Class<?> type);

    boolean existsByNameAndMaterial(String name, String material);

    @Query("""
            SELECT SUM(j.price * j.stock)
             FROM Jewelry j
            """)
    BigDecimal getTotalInventoryValue();
}
