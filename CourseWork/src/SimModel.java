import java.util.ArrayList;
import java.util.Arrays;

public class SimModel {
    public static void main(String[] args) {
        dryModel();
    }

    public static void dryModel() {
        CreateDry c = new CreateDry(10);
        SuitsSeparatorProcess p1 = new SuitsSeparatorProcess(6);
        JacketProcess p2 = new JacketProcess(5);
        PantsProcess p3 = new PantsProcess(4);
        SuitAssemblyProcess p4 = new SuitAssemblyProcess(5, 8);
        CustomerSupportProcess p5 = new CustomerSupportProcess(15);

        System.out.println("id0 = " + c.getId() + "  id1 = " + p1.getId() + " id2 = " + p2.getId() + " id3 = " + p3.getId());

        c.setNextElement(p1);
        p1.setNextElements(new ArrayList<Element>(Arrays.asList(p2, p3)));
        p2.setNextElement(p4);
        p3.setNextElement(p4);
        p4.setNextElement(p5);

        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");
        p4.setDistribution("exp");
        p5.setDistribution("exp");

        c.setName("CREATOR");
        p1.setName("PROCESS_Separate");
        p2.setName("PROCESS_Jacket");
        p3.setName("PROCESS_Pants");
        p4.setName("PROCESS_Assembly");
        p5.setName("PROCESS_Customer_Support");

        ArrayList<Element> list = new ArrayList<>(Arrays.asList(c, p1, p2, p3, p4, p5));
        ModelDry model = new ModelDry(list);
        model.simulate(1000);
    }

}
