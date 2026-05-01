package pt.ipp.estg.doa.store.model.entity;

public enum Category {
    LUXURY("Luxo"),
    CASUAL("Casual"),
    BRIDAL("Noivado/Casamento");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
