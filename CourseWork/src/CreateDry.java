public class CreateDry extends Element {
    public CreateDry(double delay) {
        super(delay);
        super.setTnext(0.01);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        super.getNextElement().inActDry(super.getTcurr());
    }
}
