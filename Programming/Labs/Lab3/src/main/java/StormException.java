public class StormException extends Exception {

    @Override
    public String getMessage() {
        return "The storm is too strong. The ship cannot move.";
    }
}
