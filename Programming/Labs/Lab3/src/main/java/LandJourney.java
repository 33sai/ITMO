public class LandJourney extends Journey {

    private Land land;

    public LandJourney(Transport transport, Land land) {
        super(transport);
        this.land = land;
    }

    @Override
    public void start() {
        try {
            transport.move();

            System.out.println("The journey continues on land.");

            for (Character c : characters) {
                c.act();
            }

            land.changeState(LandState.NORMAL);

        } catch (StormException e) {
            System.out.println(e.getMessage());
        }
    }
}
