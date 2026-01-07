import javax.xml.stream.events.Characters;
import java.lang.reflect.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Ship ship = new Ship(new Position(0, 0));
        Sea sea = new Sea(SeaState.STORM);
        Bay bay = new Bay("Quiet Bay");

        SeaJourney seaJourney = new SeaJourney(ship, sea, bay);
        seaJourney.addCharacter(new Shorty("Shorty-1"));
        seaJourney.addCharacter(new Shorty("Shorty-2"));
        Shorty shorty99 = new Shorty("Ariunbat");
        seaJourney.start();

        System.out.println("-------------------------------");

        // LAND PART
        Cart cart = new Cart(new Position(1, 1));
        Land land = new Land(LandState.ROUGH);

        LandJourney landJourney = new LandJourney(cart, land);
        landJourney.addCharacter(new Shorty("Shorty-3"));

        landJourney.start();

        Animal dog = new Animal() {
            @Override
            public void makeNoise() {
                System.out.println("Woof");
            }
        };

        ArrayList<Shorty> comparingShorties = new ArrayList<>();
        comparingShorties.add(new Shorty("Subjecto"));
        comparingShorties.add(new Shorty("Subjecta"));
        comparingShorties.add(new Shorty("Subjecte"));



        //@Ariunbat
        //Comparator
        if (shorty99.getClass().isAnnotationPresent(Ariunbat.class)) {
            System.out.println("Ariunbat");
        } else {
            System.out.println("opiuqweerpoiu");
        }


        for (Method method: shorty99.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Ariunbat.class)) {
                Ariunbat ariunbat = method.getAnnotation(Ariunbat.class);
                System.out.println(method.getName() + " is annotated with Ariunbat");
                for (int i = 0; i < ariunbat.times(); i++) {
                    try {
                        method.invoke(shorty99);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        comparingShorties.stream()
                .sorted(Shorty.BY_FEAR_THEN_NAME)
                .forEach(System.out::println);
    }
}