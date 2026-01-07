import java.util.ArrayList;

public abstract class Journey {

    protected Transport transport;
    protected ArrayList<Character> characters;

    public Journey(Transport transport) {
        this.transport = transport;
        this.characters = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }
    public abstract void start();
}
