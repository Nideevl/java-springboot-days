package order;

import order.model.*;
import order.processing.*;
import order.repository.OrderRepository;

import java.util.List;

public class Main {
    public static void main(String[] atgs){

        OrderValidator validator = new OrderValidator();
        OrderPricer pricer = new OrderPricer();
        OrderSaver saver = new OrderSaver();
        OrderNotifier notifier = new OrderNotifier();

        Item item1 = new Item("Burger", 120.0);
        Item item2 = new Item("Fries", 60.0);

        Order order1 = new Order("CUST001", List.of(item1, item2));
        Order order2 = new Order("CUST002", List.of(item1));

        DiscountOrderPricer zeroDiscount = new DiscountOrderPricer(pricer, 0);
        DiscountOrderPricer halfDiscount = new DiscountOrderPricer(pricer, 50);

        if (validator.validate(order1)) {
            zeroDiscount.price(order1);
            saver.save(order1);
            notifier.notify(order1);
        }

        if (validator.validate(order2)) {
            halfDiscount.price(order2);
            saver.save(order2);
            notifier.notify(order2);
        }

        OrderRepository<Order> Repo = new OrderRepository<>();

        Repo.addItem(order1);
        Repo.addItem(order2);

        for(Order order : Repo.getAllItems()) {
            System.out.println("Order Id "+order.getId()+" placed by "+order.getCustomerId()+" :");
            for(Item item : order.getItems()) System.out.println("- "+item.getName());
        }

    }
}
