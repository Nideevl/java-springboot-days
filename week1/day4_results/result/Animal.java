package result;

public class Animal {
    private String name;
    private String sound;

    public Animal(String name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    public void makeSound() {
        System.out.println("I am a"+getName()+" and I "+getSound());
    }
    public String getName() { return name; }
    public String getSound() { return sound; }
}
