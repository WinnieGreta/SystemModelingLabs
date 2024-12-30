import java.util.ArrayList;

public class Element {
    private String name;
    private double tnext;
    private double tcurr;
    private double delayMean, delayDev;
    private String distribution;
    private int quantity;
    private int state;
    private Element nextElement;
    private static int nextId = 0;
    private int id;

    private ArrayList<Element> nextElements;
    private double[] nextElementsProbabilities;

    public Element() {
        tnext = Double.MAX_VALUE;
        delayMean = 1.0;
        distribution = "exp";
        tcurr = tnext;
        state = 0;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public Element(double delay) {
        name = "anonymous";
        tnext = 0.0;
        delayMean = delay;
        distribution = "";
        tcurr = tnext;
        state = 0;
        nextElement = null;
        id = nextId;
        nextId++;
        //name = "element" + id;
    }

    public Element(String nameOfElement, double delay) {
        name = nameOfElement;
        tnext = 0.0;
        delayMean = delay;
        distribution = "exp";
        tcurr = tnext;
        state = 0;
        nextElement = null;
        id = nextId;
        nextId++;
    }

    public double getDelay() {
        double delay = getDelayMean();
        String dist = getDistribution().toLowerCase();
        switch (dist) {
            case "exp":
                delay = FunRand.Exp(delay);
                break;
            case "unif":
                delay = FunRand.Unif(delay, getDelayDev());
                break;
            case "norm":
                delay = FunRand.Norm(delay, getDelayDev());
                break;
        }
        return delay;
    }

    public double getDelayDev() {
        return delayDev;
    }

    public void setDelayDev(double delayDev) {
        this.delayDev = delayDev;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTcurr() {
        return tcurr;
    }

    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Element getNextElement() {
        return nextElement;
    }

    public void setNextElement(Element nextElement) {
        this.nextElement = nextElement;
    }

    public void inAct() {

    }

    public void outAct() {
        quantity++;
    }

    public double getTnext() {
        return tnext;
    }

    public void setTnext(double tnext) {
        this.tnext = tnext;
    }

    public double getDelayMean() {
        return delayMean;
    }

    public void setDelayMean(double delayMean) {
        this.delayMean = delayMean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printResult() {
        System.out.println(getName() + " quantity = " + getQuantity());
    }

    public void printInfo() {
        System.out.println(getName() + " state = " + getState() + " quantity = " + getQuantity() + " tnext = " + getTnext() + " tcurr = " + getTcurr());
    }

    public void doStatistics(double delta) {

    }

    public void setNextElements(ArrayList<Element> nextElements) {
        this.nextElements = nextElements;
    }

    public ArrayList<Element> getNextElements() {
        return nextElements;
    }

    public void setNextElementsProbabilities(double[] nextElementsProbabilities) {
        this.nextElementsProbabilities = nextElementsProbabilities;
    }

    public double[] getNextElementsProbabilities() {
        return nextElementsProbabilities;
    }
}
