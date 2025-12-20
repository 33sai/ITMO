public abstract class Character {
    protected String name;
    protected int fatigue;

    public Character(String name) {
        this.name = name;
        this.fatigue = 0;
    }

    public abstract void act();
}
