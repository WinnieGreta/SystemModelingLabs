import java.util.ArrayList;
import java.util.Arrays;

public class SimModel {
    public static void main(String[] args) {
        //simpleModelSim();
        //complexModelSim();
        //simpleModularModelSim();
        //complexModularModelSim();
        //modelTask9();
        exModel();
    }

    public static void simpleModelSim() {
        Create c = new Create(2.0);
        Process p = new Process(4.0);
        System.out.println("id0 = " + c.getId() + "  id1 = " + p.getId());
        c.setNextElement(p);
        p.setMaxqueue(5);
        c.setName("CREATOR");
        p.setName("PROCESSOR");
        c.setDistribution("exp");
        p.setDistribution("exp");

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p);
        Model model = new Model(list);
        model.simulate(1000.0);
    }

    public static void complexModelSim() {
        Create c = new Create(2.0);
        Process p1 = new Process(1.0);
        Process p2 = new Process(1.2);
        Process p3 = new Process(3.0);

        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);

        p1.setMaxqueue(5);
        p2.setMaxqueue(3);
        p3.setMaxqueue(8);

        c.setName("CREATOR");
        p1.setName("PROCESS1");
        p2.setName("PROCESS2");
        p3.setName("PROCESS3");

        c.setDistribution("unif");
        c.setDelayDev(0.3);
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);

        Model model = new Model(list);
        model.simulate(1000.0);

    }

    public static void simpleModularModelSim() {
        Create c = new Create(2.0);
        ProcessMod p = new ProcessMod(4.0);
        System.out.println("id0 = " + c.getId() + "  id1 = " + p.getId());
        c.setNextElement(p);
        p.setMaxqueue(5);
        p.setModules(3);
        c.setName("CREATOR");
        p.setName("PROCESSOR");
        c.setDistribution("exp");
        p.setDistribution("exp");

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p);
        Model model = new Model(list);
        model.simulate(1000.0);
    }

    public static void complexModularModelSim() {
        Create c = new Create(1.0);
        ProcessMod p1 = new ProcessMod(2.0);
        ProcessMod p2 = new ProcessMod(3.0);
        ProcessMod p3 = new ProcessMod(4.0);
        System.out.println("id0 = " + c.getId() + "  id1 = " + p1.getId() + "  id2 = " + p2.getId() + "  id3 = " + p3.getId());

        c.setNextElement(p1);
        p1.setNextElements(new ArrayList<Element>(Arrays.asList(p2, p3)));
        p1.setNextElementsProbabilities(new double[] {0.3, 0.7});

        p1.setMaxqueue(8);
        p2.setMaxqueue(2);
        p3.setMaxqueue(4);

        p1.setModules(2);
        p2.setModules(1);
        p3.setModules(3);

        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");

        c.setName("CREATOR");
        p1.setName("PROCESS1");
        p2.setName("PROCESS2");
        p3.setName("PROCESS3");

        ArrayList<Element> list = new ArrayList<>(Arrays.asList(c, p1, p2, p3));
        Model model = new Model(list);
        model.simulate(1000);

    }

    public static void exModel() {
        Create c = new Create(1.0);
        ProcessMod p0 = new ProcessMod(0);
        ProcessMod p1 = new ProcessMod(2.0);
        ProcessMod p2 = new ProcessMod(3.0);
        ProcessMod p3 = new ProcessMod(4.0);
        ProcessMod p4 = new ProcessMod(Integer.MAX_VALUE);
        System.out.println("id0 = " + c.getId() + "  id1 = " + p1.getId() + "  id2 = " + p2.getId() + "  id3 = " + p3.getId());

        c.setNextElement(p0);
        p0.setNextElements(new ArrayList<Element>(Arrays.asList(p1, p2)));
        p0.setNextElementsProbabilities(new double[] {0.8, 0.2});
        p1.setNextElement(p3);
        p2.setNextElement(p3);
        p3.setNextElements(new ArrayList<Element>(Arrays.asList(p1, p4)));
        p3.setNextElementsProbabilities(new double[] {0.05, 0.95});

        p1.setMaxqueue(Integer.MAX_VALUE);
        p2.setMaxqueue(0);
        p3.setMaxqueue(10);
        p4.setMaxqueue(Integer.MAX_VALUE);

        p1.setModules(1);
        p2.setModules(3);
        p3.setModules(6);

        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        p4.setDistribution("exp");

        c.setName("CREATOR");
        p1.setName("PROCESS1");
        p2.setName("PROCESS2");
        p3.setName("PROCESS3");

        ArrayList<Element> list = new ArrayList<>(Arrays.asList(c, p0, p1, p2, p3, p4));
        Model model = new Model(list);
        model.simulate(1000);
    }
}
