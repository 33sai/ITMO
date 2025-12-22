public class Sea {

    private SeaState state;

    public Sea(SeaState state) {
        this.state = state;
    }

    public void changeState(SeaState newState) {
        state = newState;
        System.out.println("Sea becomes " + state);
    }

    public SeaState getState() {
        return state;
    }
}
