public class Animal {
    private String name;
    private int age;

    public static int count;
    public Animal (String name, int age) {
        this.name = name;
        this.age = age;
        count++;
    }
    public void makeSound() {
        System.out.println("Sound");
    }
    public String toString() {
        return "Animal: " + this.name + ", age" + this.age;
    }

    
}