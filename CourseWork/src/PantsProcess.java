import java.util.ArrayList;
import java.util.List;

public class PantsProcess extends Element {
    private List<Double> queueList;
    private double meanQueue;
    private double meanState;
    private double maxQueue;
    private double statDelay;

    public PantsProcess(double delay) {
        super(delay);
        statDelay = delay;
        queueList = new ArrayList<Double>();
        this.setTnext(Double.MAX_VALUE);
        maxQueue = 0.0;
        meanQueue = 0.0;
        meanState = 0.0;
    }

    @Override
    public void inActDry(double timeInSystem) {
        System.out.println("pants in");
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
        System.out.println("pants out");
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        double next = queueList.removeFirst();
        System.out.println("removed from pants queue time " + next);
        double random = FunRand.rand.nextDouble();
        System.out.println("pants state " + random);
        if(random > 0.9) {
            next *= -1;
        }
        if (!queueList.isEmpty()) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
            System.out.println(queueList.toString());
        }
        if (super.getNextElement() != null) {
            if(super.getNextElement() instanceof  SuitAssemblyProcess) {
                ((SuitAssemblyProcess) super.getNextElement()).inActPants(next);
            } else {
                super.getNextElement().inActDry(next);
                System.out.println("sent to one next element " + next);
            }
        }

        if (super.getNextElements() != null && super.getNextElementsProbabilities() == null) {
            for(Element e : super.getNextElements()) {
                e.inActDry(next);
                System.out.println("sent to one of next elements " + next);
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

    public double getStatDelay() {
        return statDelay;
    }
}
