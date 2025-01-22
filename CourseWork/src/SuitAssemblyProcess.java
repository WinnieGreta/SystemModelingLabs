import java.util.ArrayList;
import java.util.List;

public class SuitAssemblyProcess extends Process {
    private List<Double> jacketQueueList;
    private List<Double> pantsQueueList;
    private double meanState;

    private double successDelay;
    private double failureDelay;

    private double jacketMaxQueue;
    private double pantsMaxQueue;

    private double jacketMeanQueue;
    private double pantsMeanQueue;

    private double maxTimeInSystem;
    private double meanTimeInSystem;

    private int quantityAll;
    private int quantityGood;

    public SuitAssemblyProcess(double successDelay, double failureDelay) {
        super(0.0);
        this.successDelay = successDelay;
        this.failureDelay = failureDelay;
        jacketQueueList = new ArrayList<Double>();
        pantsQueueList = new ArrayList<Double>();
        this.setTnext(Double.MAX_VALUE);
        meanState = 0.0;
        jacketMaxQueue = 0.0;
        pantsMaxQueue = 0.0;
        maxTimeInSystem = 0.0;
        meanTimeInSystem = 0.0;
        quantityAll = 0;
        quantityGood = 0;
    }

    public void inActJacket(double timeInSystem) {
        System.out.println("jacket in assembly");
        jacketQueueList.add(timeInSystem);
        if (super.getState() == 0 && !pantsQueueList.isEmpty()) {
            super.setState(1);
            if (jacketQueueList.getFirst() < 0 || pantsQueueList.getFirst() < 0) {
                super.setDelayMean(failureDelay);
            } else {
                super.setDelayMean(successDelay);
                quantityGood++;
            }
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        System.out.println("added to jacket queue assembly " + timeInSystem);
        System.out.println(jacketQueueList.toString());
        if (jacketQueueList.size() > jacketMaxQueue) {
            jacketMaxQueue = jacketQueueList.size();
        }
    }

    public void inActPants(double timeInSystem) {
        System.out.println("pants in assembly");
        pantsQueueList.add(timeInSystem);
        if (super.getState() == 0 && !jacketQueueList.isEmpty()) {
            super.setState(1);
            if (jacketQueueList.getFirst() < 0 || pantsQueueList.getFirst() < 0) {
                super.setDelayMean(failureDelay);
            } else {
                super.setDelayMean(successDelay);
                quantityGood++;
            }
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        System.out.println("added to pants queue assembly " + timeInSystem);
        System.out.println(pantsQueueList.toString());
        if (pantsQueueList.size() > pantsMaxQueue) {
            pantsMaxQueue = pantsQueueList.size();
        }
    }

    @Override
    public void outAct() {
        System.out.println("suit assembly out");
        quantityAll++;
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        double next;
        double nextJacket = jacketQueueList.removeFirst();
        double nextPants = pantsQueueList.removeFirst();
        System.out.println("removed from pants queue time " + nextPants);
        System.out.println("removed from jacket queue time " + nextJacket);
        if (!jacketQueueList.isEmpty() && !pantsQueueList.isEmpty()) {
            super.setState(1);
            if (jacketQueueList.getFirst() < 0 || pantsQueueList.getFirst() < 0) {
                super.setDelayMean(failureDelay);
                System.out.println("next suit is damaged");
            } else {
                super.setDelayMean(successDelay);
                System.out.println("next suit is undamaged");
                quantityGood++;
            }
            super.setTnext(super.getTcurr() + super.getDelay());
            System.out.println("pants left in queue: " + pantsQueueList.toString());
            System.out.println("jackets left in queue: " + jacketQueueList.toString());
        }
        next = Math.abs(nextPants);
        //System.out.println("next " + next);
        if (nextJacket < 0 || nextPants < 0) {
            if (super.getNextElement() instanceof CustomerSupportProcess) {
                System.out.println("Fault detected, sending to customer support.");
                super.getNextElement().inActDry(next);
            } else {
                System.out.println("No valid next element for faults.");
            }
        } else {
            System.out.println("calculated assembly good statistics");
            calculateTimeInSystem(next);
        }

    }

    public double getMeanState() {
        return meanState;
    }

    public double getJacketMeanQueue() {
        return jacketMeanQueue;
    }

    public double getPantsMeanQueue() {
        return pantsMeanQueue;
    }

    @Override
    public void doStatistics(double delta) {
        meanState = this.getMeanState() + this.getState() * delta;
        jacketMeanQueue = this.getJacketMeanQueue() + this.jacketQueueList.size() * delta;
        pantsMeanQueue = this.getPantsMeanQueue() + this.pantsQueueList.size() * delta;
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
        System.out.println("Good quantity = " + getQuantityGood());
        System.out.println("Max pants queue = " + pantsMaxQueue);
        System.out.println("Max jacket queue = " + jacketMaxQueue);
        System.out.println("Max time in system = " + maxTimeInSystem);
        System.out.println("Mean time in system = " + meanTimeInSystem / getQuantityGood());
    }

    public double getJacketMaxQueue() {
        return jacketMaxQueue;
    }

    public double getPantsMaxQueue() {
        return pantsMaxQueue;
    }

    @Override
    public int getQuantity() {
        return quantityAll;
    }

    public int getQuantityGood() {
        return quantityGood;
    }

    public double getMaxTimeInSystem() {
        return maxTimeInSystem;
    }

    public double getMeanTimeInSystem() {
        return meanTimeInSystem;
    }

    public double getSuccessDelay() {
        return successDelay;
    }

    public double getFailureDelay() {
        return failureDelay;
    }
}
