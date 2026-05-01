package pt.ipp.estg.doa.store.model.entity;

public enum PaymentMethod {
    CREDIT_CARD("Cartão de Crédito", "Pagamento com cartão de crédito"),
    DEBIT_CARD("Cartão de Débito", "Pagamento com cartão de débito"),
    BANK_TRANSFER("Transferência Bancária", "Transferência bancária"),
    CASH("Dinheiro", "Pagamento em dinheiro"),
    MBWAY("MB WAY", "Pagamento via MB WAY"),
    PAYPAL("PayPal", "Pagamento via PayPal");

    private final String displayName;
    private final String description;

    PaymentMethod(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean requiresReference() {
        return this == BANK_TRANSFER || this == MBWAY || this == PAYPAL;
    }

    public boolean isElectronic() {
        return this != CASH;
    }
}
