package order.processing;
import order.model.Order;
import order.model.OrderStatus;

public class OrderSaver {

    public void save(Order order) {
        order.setStatus(OrderStatus.CONFIRMED);
        System.out.println("Order ID : "+order.getId()+" got added to queue");
    }
}
