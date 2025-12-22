public class Ship extends Transport implements Movable {

    private int stability;

    public Ship(Position position) {
        super(position);
        this.stability = 100;
    }

    public void rollOnWaves() {
        stability -= 15;
        System.out.println("Ship rolls on waves. Stability = " + stability);
    }

    public void enterBay() {
        System.out.println("The ship enters a calm bay.");
    }

    @Override
    public void move() throws StormException {
        if (stability < 20) {
            throw new StormException();
        }
        System.out.println("The ship is sailing.");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Ship;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(stability);
    }

    @Override
    public String toString() {
        return "Ship{stability=" + stability + ", position=" + position + "}";
    }
}
