package orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int orderId;
    private OrderStatus status;
    private final LocalDateTime orderDate;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(int orderId) {
        this.orderId = orderId;
        this.orderDate = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    @Override
    public String toString() {
        return orderId + ", " + ", " + orderDate + ", " + items;
    }

    public List<String> toCSV() {
        List<String> lines = new ArrayList<>();

        for (OrderItem item : items){
            lines.add(item.toString());
        }
        return lines;
    }
}
