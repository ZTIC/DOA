package pt.ipp.estg.doa.store.exception;

public class OrderProcessingException extends RuntimeException{
    private final String orderId;
    private final String operation;
    private final String reason;

    public OrderProcessingException(String orderId, String operation, String reason) {
        super(String.format("Failed to %s order %s: %s", operation, orderId, reason));
        this.orderId = orderId;
        this.operation = operation;
        this.reason = reason;
    }

    public OrderProcessingException(String message) {
        super(message);
        this.orderId = null;
        this.operation = null;
        this.reason = null;
    }

    public String getOrderId() { return orderId; }
    public String getOperation() { return operation; }
    public String getReason() { return reason; }
}
