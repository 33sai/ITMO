import java.util.Objects;
import java.util.Random;

public class Shorty extends Character {

    private int fearLevel;
    private boolean awake = true;
    private static final Random RANDOM = new Random();

    public Shorty(String name) {
        super(name);
        this.fearLevel = RANDOM.nextInt(20);
    }

    public void feelFear() {
        fearLevel += RANDOM.nextInt(10);
        System.out.println(name + " feels fear. Fear level: " + fearLevel);
    }

    public void getTired() {
        fatigue += RANDOM.nextInt(15);
        if (fatigue > 100) {
            throw new ExhaustedException(name + " collapsed from exhaustion");
        }
        System.out.println(name + " gets tired. Fatigue: " + fatigue);
    }

    public void sleep() {
        awake = false;
        fatigue = Math.max(0, fatigue - 20);
        System.out.println(name + " finally falls asleep.");
    }

    public void leaveHold() {
        System.out.println(name + " leaves the dark hold.");
    }

    @Override
    public void act() {
        feelFear();
        getTired();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shorty shorty)) {
            return false;
        }
        Shorty short1 = (Shorty) o;
        if (short1.name.equals(this.name)) {
            return true;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Shorty{name='" + name + "', fear=" + fearLevel +
                ", fatigue=" + fatigue + "}";
    }
}
