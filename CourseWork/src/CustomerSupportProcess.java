import java.util.ArrayList;
import java.util.List;

public class CustomerSupportProcess extends Process {
    private List<Double> queueList;
    private double meanQueue;
    private double meanState;
    private double maxQueue;
    private double maxTimeInSystem;
    private double meanTimeInSystem;

    public CustomerSupportProcess(double delay) {
        super(delay);
        this.queueList = new ArrayList<Double>();
        this.setTnext(Double.MAX_VALUE);
        meanQueue = 0.0;
        meanState = 0.0;
        maxTimeInSystem = 0.0;
        meanTimeInSystem = 0.0;
    }

    @Override
    public void inActDry(double timeInSystem) {
        System.out.println("customer support in");
        queueList.add(timeInSystem);
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        System.out.println("added to queue " + timeInSystem);
        System.out.println(queueList.toString());
        if (queueList.size() > maxQueue) {
            maxQueue = queueList.size();
        }
    }

    public void outAct() {
        System.out.println("customer support out");
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        double next = queueList.removeFirst();
        System.out.println("removed from customer support queue time " + next);
        if(!queueList.isEmpty()) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
            System.out.println("suits left in queue " + queueList.toString());
        }
        calculateTimeInSystem(next);
    }

    public double getMeanState() {
        return meanState;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    @Override
    public void doStatistics(double delta) {
        meanState = this.getMeanState() + this.getState() * delta;
        meanQueue = this.getMeanQueue() + this.queueList.size() * delta;
    }

    public void calculateTimeInSystem(double timeOfArrival) {
        double timeInSystem = getTcurr() - timeOfArrival;
        System.out.println(timeInSystem);
        if (timeInSystem > maxTimeInSystem) {
            maxTimeInSystem = timeInSystem;
        }
        meanTimeInSystem += timeInSystem;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Max support queue = " + maxQueue);
        System.out.println("Max time in system = " + maxTimeInSystem);
        System.out.println("Mean time in system = " + meanTimeInSystem / getQuantity());
    }

    public double getMaxQueue() {
        return maxQueue;
    }

    public double getMaxTimeInSystem() {
        return maxTimeInSystem;
    }

    public double getMeanTimeInSystem() {
        return meanTimeInSystem;
    }

}
