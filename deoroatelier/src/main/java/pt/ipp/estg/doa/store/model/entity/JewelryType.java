package pt.ipp.estg.doa.store.model.entity;

public enum JewelryType {
    NECKLACE("Colar"),
    EARRING("Brinco"),
    RING("Anel");

    private final String description;

    JewelryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
