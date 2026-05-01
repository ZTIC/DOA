package pt.ipp.estg.doa.store.model.dto.response;

import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;

public class NecklaceResponse extends JewelryResponse {
    private BigDecimal length;

    public NecklaceResponse() {}

    public NecklaceResponse(Long id, String name, JewelryType jewelryType, String material,
                            BigDecimal weight, BigDecimal price, Integer stock,
                            Category category, BigDecimal length) {
        super(id, "NECKLACE", name, jewelryType, material, weight, price, stock, category);
        this.length = length;
    }

    public BigDecimal getLength() { return length; }
    public void setLength(BigDecimal length) { this.length = length; }
}
