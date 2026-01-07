public class Bay {

    private String name;

    public Bay(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bay{name='" + name + "'}";
    }
}
