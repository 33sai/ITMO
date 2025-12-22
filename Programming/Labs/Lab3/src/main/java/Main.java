public class Main {

    public static void main(String[] args) {

        Ship ship = new Ship(new Position(0, 0));
        Sea sea = new Sea(SeaState.STORM);
        Bay bay = new Bay("Quiet Bay");

        SeaJourney seaJourney = new SeaJourney(ship, sea, bay);
        seaJourney.addCharacter(new Shorty("Shorty-1"));
        seaJourney.addCharacter(new Shorty("Shorty-2"));

        seaJourney.start();

        System.out.println("-------------------------------");

        // LAND PART
        Cart cart = new Cart(new Position(1, 1));
        Land land = new Land(LandState.ROUGH);

        LandJourney landJourney = new LandJourney(cart, land);
        landJourney.addCharacter(new Shorty("Shorty-3"));

        landJourney.start();
    }
}
