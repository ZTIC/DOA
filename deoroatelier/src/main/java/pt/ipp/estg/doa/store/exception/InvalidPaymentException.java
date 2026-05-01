package pt.ipp.estg.doa.store.exception;

public class InvalidPaymentException extends RuntimeException {
    private final String reason;
    private final String paymentReference;

    public InvalidPaymentException(String reason) {
        super(String.format("Invalid payment: %s", reason));
        this.reason = reason;
        this.paymentReference = null;
    }

    public InvalidPaymentException(String reason, String paymentReference) {
        super(String.format("Invalid payment %s: %s", paymentReference, reason));
        this.reason = reason;
        this.paymentReference = paymentReference;
    }

    public String getReason() { return reason; }
    public String getPaymentReference() { return paymentReference; }
}
