package pt.ipp.estg.doa.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ipp.estg.doa.store.model.entity.Jewelry;
import pt.ipp.estg.doa.store.model.entity.Order;
import pt.ipp.estg.doa.store.model.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByOrderOrderId(Long orderId);

    List<OrderItem> findByJewelry(Jewelry jewelry);

    List<OrderItem> findByJewelryJewelryId(Long jewelryId);

    @Query("""
            SELECT SUM(oi.quantity)
             FROM OrderItem oi
              WHERE oi.jewelry.id = :jewelryId
            """)
    Long getTotalSoldQuantity(@Param("jewelryId") Long jewelryId);

    @Query("""
            SELECT oi.jewelry.id, SUM(oi.quantity) as totalSold
            FROM OrderItem oi GROUP BY oi.jewelry.id ORDER BY totalSold DESC
            """)
    List<Object[]> getBestSellingJewelry();

    @Query("""
            SELECT SUM(oi.subtotal)
             FROM OrderItem oi
              WHERE oi.order.id = :orderId
            """)
    BigDecimal getOrderSubtotal(@Param("orderId") Long orderId);

    @Query("""
            SELECT oi
             FROM OrderItem oi
              WHERE oi.priceAtPurchase != oi.jewelry.price
            """)
    List<OrderItem> findItemsWithPriceChanges();
}
