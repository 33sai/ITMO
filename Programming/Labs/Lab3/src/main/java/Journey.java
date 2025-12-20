import java.util.ArrayList;
import java.util.Random;

public class Journey {

    private Ship ship;
    private Sea sea;
    private ArrayList<Shorty> shorties;
    private static final Random RANDOM = new Random();

    public Journey() {
        ship = new Ship();
        sea = new Sea(SeaState.STORM);
        shorties = new ArrayList<>();

        int count = RANDOM.nextInt(3, 6);
        for (int i = 0; i < count; i++) {
            shorties.add(new Shorty("Shorty-" + i));
        }
    }

    public void start() {
        try {
            ship.sail();

            for (int day = 1; day <= 2; day++) {
                System.out.println("Day " + day);
                ship.rollOnWaves();
                for (Shorty s : shorties) {
                    s.act();
                }
            }

            sea.changeState(SeaState.CALM);
            ship.enterBay();

            for (Shorty s : shorties) {
                s.leaveHold();
                s.sleep();
            }

        } catch (StormException e) {
            System.out.println(e.getMessage());
        }
    }
}
