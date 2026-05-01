package orders;

import jewlry.Jewelry;
import jewlry.JewelryManager;
import payments.PaymentManager;
import payments.PaymentMethod;
import utils.CSVUtil;
import utils.Searchable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager implements Searchable {
    private final Map<Integer, Order> orders = new HashMap<>();
    private int nextId = 1;
    private final JewelryManager jewelryManager;
    private final PaymentManager paymentManager;

    public OrderManager(JewelryManager jewelryManager, PaymentManager paymentManager) {
        this.jewelryManager = jewelryManager;
        this.paymentManager = paymentManager;
    }

    public Order createOrder() {
        Order order = new Order(nextId++);
        orders.put(order.getOrderId(), order);
        return order;
    }

    public void addItem(int orderId, int jewelryId, int quantity) {
        Order order = getExistingOrder(orderId);
        Jewelry jewelry = jewelryManager.findById(jewelryId);

        if (jewelry == null) {
            throw new IllegalArgumentException("Jewelry with id " + jewelryId + " not found.");
        }
        if (jewelry.getStock() < quantity) {
            throw new IllegalArgumentException("Order with id " + orderId + " has no enough stock.");
        }

        jewelry.decreaseStock(quantity);

        OrderItem item = new OrderItem(
                jewelry.getJewelryId(),
                jewelry.getName(),
                jewelry.getPrice(),
                quantity
        );
        order.addItem(item);
    }

    public Order findById(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> findByName(String name) {
        return new ArrayList<>(orders.values());
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    private Order getExistingOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        return order;
    }

    public void save(String filename) {
        List<String> lines = orders.values()
                .stream()
                .map(Order::toString)
                .toList();
        CSVUtil.writeCSV(filename, lines);
    }

    public void payOrder(int orderId, int amount, PaymentMethod paymentMethod) {

        Order order = getExistingOrder(orderId);
        paymentManager.addPayment(order, amount, paymentMethod);

        double paid = paymentManager.getTotalPaid(order);

        if(paid == order.getTotal()) {
            order.setStatus(OrderStatus.DELIVERED);
        }
    }

    public void cancelOrder(int orderId) {

        Order order = getExistingOrder(orderId);
        for (OrderItem item : order.getItems()) {
            Jewelry jewelry = jewelryManager.findById(item.getJewelryId());
            if (jewelry != null) {
                jewelry.increaseStock(item.getQuantity());
            }
        }
        orders.remove(orderId);
    }

    public void load(String filename) {
        List<String> lines = CSVUtil.readCSV(filename);
        for (String line : lines) {
            String[] fields = line.split(",");
            int orderId = Integer.parseInt(fields[0]);
            LocalDateTime dateTime = LocalDateTime.parse(fields[1]);

            int jewelryId = Integer.parseInt(fields[2]);
            String name = fields[3];
            double price = Double.parseDouble(fields[4]);
            int quantity = Integer.parseInt(fields[5]);

            Order order = orders.computeIfAbsent(orderId, Order::new);
            order.addItem(new OrderItem(jewelryId, name, price, quantity));

            nextId = Math.max(nextId, orderId + 1);
        }
    }
}
