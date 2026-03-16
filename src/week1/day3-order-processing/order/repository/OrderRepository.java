package order.repository;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository<T> {
    private List<T> items;

    public OrderRepository() {
        this.items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }

}
