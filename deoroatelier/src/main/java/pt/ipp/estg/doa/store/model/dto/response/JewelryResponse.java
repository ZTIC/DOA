package pt.ipp.estg.doa.store.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class JewelryResponse {

    private Long id;
    private String type;
    private String name;
    private JewelryType jewelryType;
    private String material;
    private BigDecimal weight;
    private BigDecimal price;
    private Integer stock;
    private Category category;
    private boolean inStock;
    private BigDecimal totalValue;

    protected JewelryResponse() {
    }

    protected JewelryResponse(Long id, String type, String name, JewelryType jewelryType,
                              String material, BigDecimal weight, BigDecimal price,
                              Integer stock, Category category) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.jewelryType = jewelryType;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.inStock = stock > 0;
        this.totalValue = price.multiply(BigDecimal.valueOf(stock));
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JewelryType getJewelryType() {
        return jewelryType;
    }

    public void setJewelryType(JewelryType jewelryType) {
        this.jewelryType = jewelryType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
