package pt.ipp.estg.doa.store.model.dto.response;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long id;
    private Long jewelryId;
    private String jewelryName;
    private String jewelryType;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
    private BigDecimal subtotal;

    public OrderItemResponse() {}

    public OrderItemResponse(Long id, Long jewelryId, String jewelryName, String jewelryType,
                             Integer quantity, BigDecimal priceAtPurchase, BigDecimal subtotal) {
        this.id = id;
        this.jewelryId = jewelryId;
        this.jewelryName = jewelryName;
        this.jewelryType = jewelryType;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.subtotal = subtotal;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJewelryId() { return jewelryId; }
    public void setJewelryId(Long jewelryId) { this.jewelryId = jewelryId; }

    public String getJewelryName() { return jewelryName; }
    public void setJewelryName(String jewelryName) { this.jewelryName = jewelryName; }

    public String getJewelryType() { return jewelryType; }
    public void setJewelryType(String jewelryType) { this.jewelryType = jewelryType; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
