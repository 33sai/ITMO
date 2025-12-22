public abstract class Transport {

    protected Position position;

    public Transport(Position position) {
        this.position = position;
    }

    public abstract void move() throws StormException;

    @Override
    public String toString() {
        return "Transport{position=" + position + "}";
    }
}
