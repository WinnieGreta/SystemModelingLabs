import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        recordResults("output_experiment.csv ");
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

    public void recordResults(String filename) {
        try {
            File file = new File(filename);
            boolean isNewFile = !file.exists();

            try (FileWriter writer = new FileWriter(file, true)) { // Append mode
                StringBuilder headers = new StringBuilder();
                StringBuilder values = new StringBuilder();
                StringBuilder delayHeaders = new StringBuilder();
                StringBuilder delayValues = new StringBuilder();

                if (isNewFile) {
                    headers.append("Time");
                    delayHeaders.append("Time");
                }
                values.append(tcurr);
                delayValues.append(tcurr);

                for (Element e : list) {
                    if (e instanceof CreateDry) {
                        CreateDry s = (CreateDry) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_Quantity");
                            delayHeaders.append("," + e.getName() + "_Delay");
                        }
                        values.append("," + s.getQuantity());
                        delayValues.append("," + s.getStatDelay());
                    } else if (e instanceof SuitsSeparatorProcess) {
                        SuitsSeparatorProcess s = (SuitsSeparatorProcess) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_MeanQueue," + e.getName() + "_MaxQueue," + e.getName() + "_MeanState");
                            delayHeaders.append("," + e.getName() + "_Delay");
                        }
                        values.append("," + (s.getMeanQueue() / tcurr) + "," + s.getMaxQueue() + "," + (s.getMeanState() / tcurr));
                        delayValues.append("," + s.getStatDelay());
                    } else if (e instanceof JacketProcess) {
                        JacketProcess s = (JacketProcess) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_MeanQueue," + e.getName() + "_MaxQueue," + e.getName() + "_MeanState");
                            delayHeaders.append("," + e.getName() + "_Delay");
                        }
                        values.append("," + (s.getMeanQueue() / tcurr) + "," + s.getMaxQueue() + "," + (s.getMeanState() / tcurr));
                        delayValues.append("," + s.getStatDelay());
                    } else if (e instanceof PantsProcess) {
                        PantsProcess s = (PantsProcess) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_MeanQueue," + e.getName() + "_MaxQueue," + e.getName() + "_MeanState");
                            delayHeaders.append("," + e.getName() + "_Delay");
                        }
                        values.append("," + (s.getMeanQueue() / tcurr) + "," + s.getMaxQueue() + "," + (s.getMeanState() / tcurr));
                        delayValues.append("," + s.getStatDelay());
                    } else if (e instanceof SuitAssemblyProcess) {
                        SuitAssemblyProcess s = (SuitAssemblyProcess) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_JacketMeanQueue," + e.getName() + "_JacketMaxQueue," + e.getName() + "_MeanState," + e.getName() + "_UndamagedProcessed," + e.getName() + "_MaxTimeInSystem," + e.getName() + "_MeanTimeInSystem");
                            delayHeaders.append("," + e.getName() + "_SuccessDelay," + e.getName() + "_FailureDelay");
                        }
                        values.append("," + (s.getJacketMeanQueue() / tcurr) + "," + s.getJacketMaxQueue() + "," + (s.getMeanState() / tcurr) + "," + s.getQuantityGood() + "," + s.getMaxTimeInSystem() + "," + (s.getMeanTimeInSystem() / s.getQuantityGood()));
                        delayValues.append("," + s.getSuccessDelay() + "," + s.getFailureDelay());
                    } else if (e instanceof CustomerSupportProcess) {
                        CustomerSupportProcess s = (CustomerSupportProcess) e;
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_MeanQueue," + e.getName() + "_MaxQueue," + e.getName() + "_MeanState," + e.getName() + "_MaxTimeInSystem," + e.getName() + "_MeanTimeInSystem");
                            delayHeaders.append("," + e.getName() + "_Delay");
                        }
                        values.append("," + (s.getMeanQueue() / tcurr) + "," + s.getMaxQueue() + "," + (s.getMeanState() / tcurr) + "," + s.getMaxTimeInSystem() + "," + (s.getMeanTimeInSystem() / s.getQuantity()));
                        delayValues.append("," + s.getStatDelay());
                    } else {
                        if (isNewFile) {
                            headers.append("," + e.getName() + "_N/A");
                            delayHeaders.append("," + e.getName() + "_N/A");
                        }
                        values.append(",N/A");
                        delayValues.append(",N/A");
                    }
                }

                if (isNewFile) {
                    writer.write(delayHeaders.toString() + ",");
                    writer.write(headers.toString() + "\n");
                }
                writer.write(delayValues.toString() + ",");
                writer.write(values.toString() + "\n");
                System.out.println("Results successfully appended to " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
