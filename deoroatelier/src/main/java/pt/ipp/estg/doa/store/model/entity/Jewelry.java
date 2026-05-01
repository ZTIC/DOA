package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "jewelry_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "jewelry_tb")
public abstract class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jewelryId;

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String name;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private JewelryType type;

    @NotBlank(message = "Material is required")
    @Column(nullable = false)
    private String material;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must be zero or positive")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Version
    private Integer version;

    // Constructors
    protected Jewelry() {}

    protected Jewelry(String name, JewelryType type, String material, BigDecimal weight,
                      BigDecimal price, Integer stock, Category category) {
        this.name = name;
        this.type = type;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters e Setters
    public Long getId() { return jewelryId; }
    public void setId(Long jewelryId) { this.jewelryId = jewelryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public JewelryType getType() { return type; }
    public void setType(JewelryType type) { this.type = type; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    // Business methods
    public abstract String getJewelryType();

    public boolean isInStock() {
        return stock > 0;
    }

    public boolean hasSufficientStock(int requestedQuantity) {
        return stock >= requestedQuantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (!hasSufficientStock(quantity)) {
            throw new IllegalStateException(
                    String.format("Insufficient stock. Available: %d, Requested: %d",
                            stock, quantity));
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stock += quantity;
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = newPrice;
    }

    public BigDecimal getTotalValue() {
        return price.multiply(BigDecimal.valueOf(stock));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jewelry)) return false;
        Jewelry jewelry = (Jewelry) o;
        return jewelryId != null && Objects.equals(jewelryId, jewelry.jewelryId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Jewelry{id=%d, name='%s', type=%s, price=%.2f, stock=%d}",
                jewelryId, name, type, price, stock);
    }
}
