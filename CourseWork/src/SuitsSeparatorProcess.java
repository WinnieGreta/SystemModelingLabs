import java.util.ArrayList;
import java.util.List;

public class SuitsSeparatorProcess extends Element {
    private List<Double> queueList;
    private double meanQueue;
    private double meanState;
    private double maxQueue;

    public SuitsSeparatorProcess(double delay) {
        super(delay);
        queueList = new ArrayList<Double>();
        this.setTnext(Double.MAX_VALUE);
        maxQueue = 0.0;
        meanQueue = 0.0;
        meanState = 0.0;
    }

    @Override
    public void inActDry(double timeInSystem) {
        System.out.println("separator in");
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        queueList.add(timeInSystem);
        System.out.println("added to queue " + timeInSystem);
        System.out.println(queueList.toString());
        if (queueList.size() > maxQueue) {
            maxQueue = queueList.size();
        }
    }

    @Override
    public void outAct() {
        System.out.println("separator out");
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        double next = queueList.removeFirst();
        System.out.println("removed from separator queue time " + next);
        if (!queueList.isEmpty()) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
            System.out.println(queueList.toString());
        }
        if (super.getNextElement() != null) {
            super.getNextElement().inActDry(next);
            System.out.println("sent to one next element");
        }
        if (super.getNextElements() != null && super.getNextElementsProbabilities() == null) {
            for(Element e : super.getNextElements()) {
                e.inActDry(next);
                System.out.println("sent to one of next elements");
            }
        }
    }

    public double getMeanState() {
        return meanState;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = this.getMeanQueue() + this.queueList.size() * delta;
        meanState = this.getMeanState() + this.getState() * delta;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Max queue = " + this.getMaxQueue());
    }

    public double getMaxQueue() {
        return maxQueue;
    }

}
