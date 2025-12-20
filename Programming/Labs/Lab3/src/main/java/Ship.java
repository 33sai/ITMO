import java.util.Objects;

public class Ship implements Sailable {

    private int stability = 100;
    private Position position = new Position(0, 0);

    public void rollOnWaves() {
        stability -= 10;
        System.out.println("The ship rolls on the waves. Stability: " + stability);
    }

    public void enterBay() {
        System.out.println("The ship enters a calm bay.");
    }

    @Override
    public void sail() throws StormException {
        if (stability < 20) {
            throw new StormException();
        }
        System.out.println("The ship is sailing.");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Ship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stability);
    }

    @Override
    public String toString() {
        return "Ship{stability=" + stability + "}";
    }
}
