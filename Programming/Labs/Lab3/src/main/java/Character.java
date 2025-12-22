public abstract class Character {

    protected String name;
    protected int fatigue;

    public Character(String name) {
        this.name = name;
        this.fatigue = 0;
    }

    public abstract void act();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Character other)) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Character{name='" + name + "', fatigue=" + fatigue + "}";
    }
}
