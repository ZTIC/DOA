package pt.ipp.estg.doa.store.exception;

public class OutOfStockException extends RuntimeException{
    private final String jewelryName;
    private final Long jewelryId;
    private final int availableStock;
    private final int requestedQuantity;

    public OutOfStockException(String jewelryName, Long jewelryId,
                               int availableStock, int requestedQuantity) {
        super(String.format("Jewelry '%s' (ID: %d) is out of stock. Available: %d, Requested: %d",
                jewelryName, jewelryId, availableStock, requestedQuantity));
        this.jewelryName = jewelryName;
        this.jewelryId = jewelryId;
        this.availableStock = availableStock;
        this.requestedQuantity = requestedQuantity;
    }

    public OutOfStockException(String message) {
        super(message);
        this.jewelryName = null;
        this.jewelryId = null;
        this.availableStock = 0;
        this.requestedQuantity = 0;
    }

    public String getJewelryName() { return jewelryName; }
    public Long getJewelryId() { return jewelryId; }
    public int getAvailableStock() { return availableStock; }
    public int getRequestedQuantity() { return requestedQuantity; }
}
