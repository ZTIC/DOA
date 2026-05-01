package pt.ipp.estg.doa.store.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("EARRING")
@Table(name = "earring_tb")
public class Earring extends Jewelry{
    @NotBlank(message = "Clasp type is required")
    @Column(name = "clasp_type", nullable = false)
    private String claspType;

    // Construtores
    protected Earring() {}

    public Earring(String name, String material, BigDecimal weight, BigDecimal price,
                   Integer stock, Category category, String claspType) {
        super(name, JewelryType.EARRING, material, weight, price, stock, category);
        this.claspType = claspType;
    }

    // Getters e Setters
    public String getClaspType() { return claspType; }
    public void setClaspType(String claspType) { this.claspType = claspType; }

    @Override
    public String getJewelryType() {
        return "EARRING";
    }

    @Override
    public String toString() {
        return String.format("Earring{id=%d, name='%s', claspType='%s', price=%.2f, stock=%d}",
                getId(), getName(), claspType, getPrice(), getStock());
    }
}
