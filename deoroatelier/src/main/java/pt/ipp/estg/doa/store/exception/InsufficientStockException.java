package pt.ipp.estg.doa.store.exception;

public class InsufficientStockException extends RuntimeException{

    private final String jewelryName;
    private final Long jewelryId;
    private final int availableStock;
    private final int requestedQuantity;

    public InsufficientStockException(String jewelryName, Long jewelryId,
                                      int availableStock, int requestedQuantity) {
        super(String.format("Insufficient stock for jewelry '%s' (ID: %d). Available: %d, Requested: %d",
                jewelryName, jewelryId, availableStock, requestedQuantity));
        this.jewelryName = jewelryName;
        this.jewelryId = jewelryId;
        this.availableStock = availableStock;
        this.requestedQuantity = requestedQuantity;
    }

    public String getJewelryName() { return jewelryName; }
    public Long getJewelryId() { return jewelryId; }
    public int getAvailableStock() { return availableStock; }
    public int getRequestedQuantity() { return requestedQuantity; }
}
