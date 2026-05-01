package orders;

import customers.Customer;
import jewlry.Necklace;

public class OrderItem {
    private final int jewelryId;
    private final String jewelryName;
    private final double unitPrice;
    private int quantity;

    public OrderItem(int jewelryId, String jewelryName, double unitPrice, int quantity) {
        if (jewelryId <= 0){
            throw new IllegalArgumentException("jewelryId must be greater than 0");
        }
        this.jewelryId = jewelryId;
        this.jewelryName = jewelryName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getJewelryId() {
        return jewelryId;
    }

    public String getJewelryName() {
        return jewelryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
        this.quantity += quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }
}
