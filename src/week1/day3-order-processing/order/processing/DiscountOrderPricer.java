package order.processing;
import order.model.Order;

public class DiscountOrderPricer {
    private final OrderPricer pricer;
    private final double discountPercent;

    public DiscountOrderPricer(OrderPricer pricer, double discountPercent){
        this.pricer = pricer;
        this.discountPercent = discountPercent;
    }

    public void price(Order order) {
        pricer.price(order);
        double discountedPrice = order.getTotalAmount()*(1 - discountPercent/100);
        order.setTotalAmount(discountedPrice);
        System.out.println("The discounted prize is "+discountedPrice+" for Order Id : "+order.getId());
    }
}
