package pt.ipp.estg.doa.store.model.entity;

public enum OrderStatus {
    PENDING("Pendente", "Aguardando processamento"),
    ACCEPTED("Aceite", "Pedido aceite, stock reservado"),
    DELIVERED("Entregue", "Pedido entregue ao cliente"),
    CANCELED("Cancelado", "Pedido cancelado");

    private final String displayName;
    private final String description;

    OrderStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean canTransitionTo(OrderStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == ACCEPTED || newStatus == CANCELED;
            case ACCEPTED -> newStatus == DELIVERED || newStatus == CANCELED;
            case DELIVERED, CANCELED -> false; // Terminal states
        };
    }

    public boolean isTerminal() {
        return this == DELIVERED || this == CANCELED;
    }

    public boolean canModify() {
        return this == PENDING || this == ACCEPTED;
    }
}
