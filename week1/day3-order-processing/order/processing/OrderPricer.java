package order.processing;

import order.model.Order;
import order.model.Item;

public class OrderPricer {

    public void price(Order order) {
        order.setTotalAmount(order.getItems().stream().mapToDouble(Item::getPrize).sum());
        System.out.println("Order ID : "+order.getId()+" priced at "+order.getTotalAmount());
    }
}
