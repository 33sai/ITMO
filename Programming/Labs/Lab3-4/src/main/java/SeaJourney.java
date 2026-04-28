public class SeaJourney extends Journey {

    private Sea sea;
    private Bay bay;

    public SeaJourney(Transport transport, Sea sea, Bay bay) {
        super(transport);
        this.sea = sea;
        this.bay = bay;
    }

    @Override
    public void start() {
        try {
            transport.move();

            for (int i = 0; i < 2; i++) {
                System.out.println("The ship is being thrown by waves.");
                ((Ship) transport).rollOnWaves();

                for (Character c : characters) {
                    c.act();
                }
            }

            sea.changeState(SeaState.CALM);
            ((Ship) transport).enterBay();
            System.out.println("Destination: " + bay);

            for (Character c : characters) {
                if (c instanceof Shorty s) {
                    s.sleep();
                }
            }

        } catch (StormException e) {
            System.out.println(e.getMessage());
        }
    }
}
