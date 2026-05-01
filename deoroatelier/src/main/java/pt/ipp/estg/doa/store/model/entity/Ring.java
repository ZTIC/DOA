package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "ring_tb")
@DiscriminatorValue("RING")
public class Ring extends Jewelry {
    @NotNull(message = "Size is required")
    @Min(value = 10, message = "Ring size must be at least 10 (European standard)")
    @Max(value = 30, message = "Ring size must not exceed 30 (European standard)")
    @Column(nullable = false)
    private Integer size;

    // Construtores
    protected Ring() {}

    public Ring(String name, String material, BigDecimal weight, BigDecimal price,
                Integer stock, Category category, Integer size) {
        super(name, JewelryType.RING, material, weight, price, stock, category);
        this.size = size;
    }

    // Getters e Setters
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    @Override
    public String getJewelryType() {
        return "RING";
    }

    @Override
    public String toString() {
        return String.format("Ring{id=%d, name='%s', size=%d, price=%.2f, stock=%d}",
                getId(), getName(), size, getPrice(), getStock());
    }
}
