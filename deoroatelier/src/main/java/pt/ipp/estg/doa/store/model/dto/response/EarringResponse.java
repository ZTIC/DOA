package pt.ipp.estg.doa.store.model.dto.response;

import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;

public class EarringResponse extends JewelryResponse{
    private String claspType;

    public EarringResponse() {}

    public EarringResponse(Long id, String name, JewelryType jewelryType, String material,
                           BigDecimal weight, BigDecimal price, Integer stock,
                           Category category, String claspType) {
        super(id, "EARRING", name, jewelryType, material, weight, price, stock, category);
        this.claspType = claspType;
    }

    public String getClaspType() { return claspType; }
    public void setClaspType(String claspType) { this.claspType = claspType; }

}
