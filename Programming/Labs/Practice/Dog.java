public class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println("Woof");
    }

    @Override
    public String toString() {
        return "Dog: " + super.toString();
    }
    
}