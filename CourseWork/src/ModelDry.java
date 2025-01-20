import java.util.ArrayList;
import java.util.stream.Collectors;

public class ModelDry {
    private ArrayList<Element> list;
    private double tcurr;
    private double tnext;

    public ModelDry(ArrayList<Element> elements) {
        this.list = elements;
        tcurr = 0.0;
        tnext = Double.MAX_VALUE;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            tnext = Double.MAX_VALUE;
            Element nextElement = null;
            for (Element e : list) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    nextElement = e;
                }
            }
            System.out.println("\nIt's time for event in " + nextElement.getName() + ", time = " + tnext);
            for (Element e : list) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;
            for (Element e : list) {
                e.setTcurr(tcurr);
            }
            nextElement.outAct();
            for (Element e : list) {
                if (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }
            printInfo();

        }
        printResult();
    }

    public void printInfo() {
        for (Element e : list) {
            e.printInfo();
        }
    }

    public  void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            System.out.println("----------------------------");
            e.printResult();

            if (e instanceof SuitsSeparatorProcess) {
                SuitsSeparatorProcess s = (SuitsSeparatorProcess) e;
                System.out.println("mean length of queue = " + s.getMeanQueue() / tcurr +
                        "\nmax length of queue = " + s.getMaxQueue() +
                        "\nmean state = " + s.getMeanState() / tcurr);
            }

            if (e instanceof JacketProcess) {
                JacketProcess s = (JacketProcess) e;
                System.out.println("mean length of queue = " + s.getMeanQueue() / tcurr +
                        "\nmax length of queue = " + s.getMaxQueue() +
                        "\nmean state = " + s.getMeanState() / tcurr);
            }

            if (e instanceof PantsProcess) {
                PantsProcess s = (PantsProcess) e;
                System.out.println("mean length of queue = " + s.getMeanQueue() / tcurr +
                        "\nmax length of queue = " + s.getMaxQueue() +
                        "\nmean state = " + s.getMeanState() / tcurr);
            }

            if (e instanceof SuitAssemblyProcess) {
                SuitAssemblyProcess s = (SuitAssemblyProcess) e;
                System.out.println("mean length of jacket queue = " + s.getJacketMeanQueue() / tcurr +
                        "\nmean length of pants queue = " + s.getPantsMeanQueue() / tcurr +
                        "\nmax length of jacket queue = " + s.getJacketMaxQueue() +
                        "\nmax length of pants queue = " + s.getPantsMaxQueue() +
                        "\nmean state = " + s.getMeanState() / tcurr +
                        "\nundamaged suits processed = " + s.getQuantityGood() +
                        "\nmax time in system for undamaged suits = " + s.getMaxTimeInSystem() +
                        "\nmean time in system for undamaged suits = " + s.getMeanTimeInSystem() / s.getQuantityGood());
            }

            if (e instanceof CustomerSupportProcess) {
                CustomerSupportProcess s = (CustomerSupportProcess) e;
                System.out.println("mean length of queue = " + s.getMeanQueue() / tcurr +
                        "\nmax length of queue = " + s.getMaxQueue() +
                        "\nmean state = " + s.getMeanState() / tcurr +
                        "\nmax time in system for damaged suits = " + s.getMaxTimeInSystem() +
                        "\nmean time in system for damaged suits = " + s.getMeanTimeInSystem() / s.getQuantity());
            }
        }
    }
}
