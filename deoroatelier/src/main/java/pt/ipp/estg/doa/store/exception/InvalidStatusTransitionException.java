package pt.ipp.estg.doa.store.exception;

import pt.ipp.estg.doa.store.model.entity.OrderStatus;

public class InvalidStatusTransitionException extends RuntimeException{
    private final OrderStatus currentStatus;
    private final OrderStatus attemptedStatus;

    public InvalidStatusTransitionException(OrderStatus currentStatus, OrderStatus attemptedStatus) {
        super(String.format("Cannot transition order from %s to %s",
                currentStatus, attemptedStatus));
        this.currentStatus = currentStatus;
        this.attemptedStatus = attemptedStatus;
    }

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public OrderStatus getAttemptedStatus() {
        return attemptedStatus;
    }
}
