package order.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String id;
    private String customerId;
    private List<Item> items;
    private double totalAmount;
    private OrderStatus status;

    public Order(String customerId, List<Item> items) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
    }
    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
    }
    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public List<Item> getItems() { return new ArrayList<>(items); }
    public double getTotalAmount() { return totalAmount; }

}
