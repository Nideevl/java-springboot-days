import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Result<String> ok = Result.success("Order Placed");
        if(ok.isSuccess()) System.out.println(ok.getData());

        Result<String> fail = Result.failure("No items in order");
        if(!fail.isSuccess()) System.out.println(fail.getError());

        List<Dog> dogs = new ArrayList<>(List.of(new Dog("Rex", "Woof"), new Dog("Bruno", "Woof")));
        List<Cat> cats = new ArrayList<>(List.of(new Cat("Whiskers", "Meow")));

        printNames(dogs);
        printNames(cats);

        List<Animal> animals = new ArrayList<>();
        addDog(animals);
        System.out.println(animals.get(0).getName());
    }

    static void printNames(List<? extends Animal> animals) {
        for(Animal a : animals) System.out.println("My name is " + a.getName());
    }

    static void addDog(List<? super Dog> list) {
        list.add(new Dog("Tom", "woof"));
    }
}