package order.processing;

import order.model.Order;

public class OrderValidator {

    public boolean validate(Order order) {
        if(order.getItems().isEmpty()) {
            System.out.println("Order Id : "+order.getId()+" is invalid - no items");
            return false;
        }
        else {
            System.out.println("Order Id : "+order.getId()+" valid");
            return true;
        }
    }
}
