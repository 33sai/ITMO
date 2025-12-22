public class Land {

    private LandState condition;

    public Land(LandState condition) {
        this.condition = condition;
    }

    public void changeState(LandState newState) {
        condition = newState;
        System.out.println("Land condition is now " + condition);
    }

    public LandState getCondition() {
        return condition;
    }
}
