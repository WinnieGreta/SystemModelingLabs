import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessMod extends Element {
    private int queue, maxqueue, failure;
    private double meanQueue;
    private double meanState;
    private int nModules;

    private ArrayList<Process> modules;

    public ProcessMod(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        meanState = 0.0;
        nModules = 1;
        modules = new ArrayList<Process>();
        Process p = new Process(delay);
        p.setMaxqueue(0);
        p.setTnext(Double.MAX_VALUE);
        modules.add(p);
    }

    @Override
    public void inAct() {
        int failedModules = 0;
        //should be used if every module had its own queue or the modules can be worn out
        //Collections.shuffle(modules);
        if (this.getState() < nModules) {
            for (Process p : modules) {
                int failuresBefore = p.getFailure();
                p.setTcurr(getTcurr());
                p.inAct();
                //stops after first success
                if (failuresBefore == p.getFailure()) {
                    System.out.println("in act success");
                    this.setState(this.getState() + 1);
                    System.out.println("this state " + this.getState());
                    break;
                } else {
                    System.out.println("module failed");
                    failedModules++;
                }
            }
            // If every module has failed
            if (failedModules == nModules) {
                //System.out.println("module failed");
                failure++;
            }
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
                System.out.println("queue " + getQueue());
            }
            else {
                //failure due to the queue being full
                System.out.println("queue full");
                failure++;
            }
        }

    }

    @Override
    public void outAct() {
        super.outAct();
        //super.setTnext(Double.MAX_VALUE);
        this.setState(this.getState() - 1);
        Collections.min(modules, Comparator.comparingDouble(Process::getTnext)).outAct();
        System.out.println("out act happening");
        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            System.out.println("queue from out " + getQueue());
            this.setState(this.getState() + 1);
            Collections.min(modules, Comparator.comparingDouble(Process::getTnext)).inAct();
            if(getTnextFromModules() != Double.MAX_VALUE) {
                super.setTnext(getTnextFromModules());
            } else {
                System.out.println("all modules are cooked");
            }
        }
        //this stays, it's for model as a whole
        if (super.getNextElement() != null) {
            super.getNextElement().inAct();
        }
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
//        for(Process m : modules) {
//            m.setQueue(queue);
//        }
        this.queue = queue;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
//        for(Process m : modules) {
//            m.setMaxqueue(maxqueue);
//        }
        this.maxqueue = maxqueue;
    }

    public int getModules() {
        return nModules;
    }

    public void setModules(int n) {
        Process p = modules.getFirst();
        modules.clear();
        for (int i = 0; i < n; i++) {
            try {
                modules.add((Process) p.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        this.nModules = n;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = this.getMeanQueue() + this.queue * delta;
        meanState = this.getMeanState() + this.getState() * delta;
    }

    public double getMeanState() {
        return meanState;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

//    @Override
//    public int getState() {
//        int state = 0;
//        for(Process m : modules) {
//            state += m.getState();
//        }
//        return state;
//    }

    public float getStateNormalized() {
        return ((float) this.getState()) / nModules;
    }

    @Override
    public double getTnext() {
        return Collections.min(modules, Comparator.comparingDouble(Process::getTnext)).getTnext();
    }

    @Override
    public void setTcurr(double tcurr) {
        super.setTcurr(tcurr);
        for (Process m : modules) {
            m.setTcurr(tcurr);
        }
    }

    @Override
    public void setDistribution(String distribution) {
        super.setDistribution(distribution);
        for (Process m : modules) {
            m.setDistribution(distribution);
        }
    }

    public double getTnextFromModules() {
        return Collections.min(modules, Comparator.comparingDouble(Process::getTnext)).getTnext();
    }
}
