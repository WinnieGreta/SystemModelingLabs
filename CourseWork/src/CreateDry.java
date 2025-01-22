public class CreateDry extends Element {
    private double statDelay;

    public CreateDry(double delay) {
        super(delay);
        statDelay = delay;
        super.setTnext(0.01);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        super.getNextElement().inActDry(super.getTcurr());
    }

    public double getStatDelay() {
        return statDelay;
    }
}
