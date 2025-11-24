public class Main {
    public static void main(String[] args) {
        Animal[] animals = {
            new Dog("John", 1),
            new Cat("Luna", 2)
        };

        for (Animal a: animals) {
            System.out.print(a + " ");
            a.makeSound();
        }

        System.out.println("Total animals created: " + Animal.count);
    }
}