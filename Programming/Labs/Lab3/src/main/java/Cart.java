public class Cart extends Transport implements Movable {
    private int durability;

    public Cart(Position position) {
        super(position);
        this.durability = 100;
    }

    public void shakeOnRoad() {
        durability -= 10;
        System.out.println("The cart shakes on the road. Durability = " + this.durability);
    }

    @Override
    public void move() throws StormException {
        if (this.durability < 20) {
            throw new StormException();
        }
        System.out.println("The cart moves along the land.");
    }

    @Override
    public String toString() {
        return "Cart{durability=" + durability + ", position=" + position + "}";
    }
}
