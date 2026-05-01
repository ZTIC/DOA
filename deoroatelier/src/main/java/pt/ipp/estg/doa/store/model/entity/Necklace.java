package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "necklace_tb")
@DiscriminatorValue("NECKLACE")
public class Necklace extends Jewelry {

    @NotNull(message = "Length is required")
    @Positive(message = "Length must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal length;

    // Construtores
    protected Necklace() {
    }

    public Necklace(String name, String material, BigDecimal weight, BigDecimal price,
                    Integer stock, Category category, BigDecimal length) {
        super(name, JewelryType.NECKLACE, material, weight, price, stock, category);
        this.length = length;
    }

    // Getters e Setters
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    @Override
    public String getJewelryType() {
        return "NECKLACE";
    }

    @Override
    public String toString() {
        return String.format("Necklace{id=%d, name='%s', length=%.2fcm, price=%.2f, stock=%d}",
                getId(), getName(), length, getPrice(), getStock());
    }
}
