import java.util.Comparator;
import java.util.Random;
public class Shorty extends Character {

    private int fear;
    private boolean awake;
    private static final Random RANDOM = new Random();

    public Shorty(String name) {
        super(name);
        this.fear = 0;
        this.awake = true;
    }
    @Ariunbat(times = 3)
    public void feelFear() {
        fear += RANDOM.nextInt(10);
        System.out.println(name + " feels fear. Fear = " + fear);
    }
    @Ariunbat(times = 4)
    public void getTired() {
        fatigue += RANDOM.nextInt(15);
        if (fatigue > 100) {
            throw new ExhaustedException(name + " collapsed from exhaustion.");
        }
        System.out.println(name + " gets tired. Fatigue = " + fatigue);
    }

    public int getFear() {
        return this.fear;
    }

    public String getName() {
        return this.name;
    }

    @Ariunbat
    public void sleep() {
        awake = false;
        fatigue = Math.max(0, fatigue - 30);
        System.out.println(name + " finally falls asleep.");
    }

    @Override
    public void act() {
        feelFear();
        getTired();
    }

    @Override
    public String toString() {
        return "Shorty{name='" + name + "', fear=" + fear +
                ", fatigue=" + fatigue + ", awake=" + awake + "}";
    }

    public static final Comparator<Shorty> BY_FEAR_THEN_NAME =
            Comparator.comparingInt(Shorty::getFear)
                    .thenComparing(Shorty::getName);
}
