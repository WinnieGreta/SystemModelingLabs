import java.util.ArrayList;

public class SimModel {
    public static void main(String[] args) {
        //simpleModelSim();
        complexModelSim();
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
        Process p3 = new Process(4.0);

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
}
