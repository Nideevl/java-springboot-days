package order.processing;

import order.model.Order;

public class OrderNotifier {
    public void notify(Order order) {
        System.out.println("Customer "+order.getCustomerId()+" notified for Order ID : "+order.getId());
    }
}
