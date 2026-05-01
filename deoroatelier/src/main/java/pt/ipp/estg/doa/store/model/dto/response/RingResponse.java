package pt.ipp.estg.doa.store.model.dto.response;

import pt.ipp.estg.doa.store.model.entity.Category;
import pt.ipp.estg.doa.store.model.entity.JewelryType;

import java.math.BigDecimal;

public class RingResponse extends JewelryResponse{
    private Integer size;

    public RingResponse() {}

    public RingResponse(Long id, String name, JewelryType jewelryType, String material,
                        BigDecimal weight, BigDecimal price, Integer stock,
                        Category category, Integer size) {
        super(id, "RING", name, jewelryType, material, weight, price, stock, category);
        this.size = size;
    }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}
