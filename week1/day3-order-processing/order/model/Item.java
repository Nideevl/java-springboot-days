package order.model;
import order.contract.priceable;

public class Item implements priceable{
    private double prize;
    private String name;

    public Item(String name, double prize) {
        this.prize = prize;
        this.name = name;
    }

    @Override
    public double getPrize() { return prize; }

    @Override
    public String getName() { return name; }
}
