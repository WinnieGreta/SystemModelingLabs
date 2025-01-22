import java.util.ArrayList;
import java.util.Arrays;

public class SimModel {
    public static void main(String[] args) {
        //verify();
        experiment();
    }

    public static void experiment() {
        for (int i = 0; i < 10; i++) {
            dryModel(8, 8, 7, 6, 4, 6, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 8, 7, 6, 4, 6, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 8, 3, 2, 4, 9, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 8, 3, 2, 4, 9, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 4, 7, 2, 6, 6, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 4, 7, 2, 6, 6, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 4, 3, 6, 6, 9, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(8, 4, 3, 6, 6, 9, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 4, 3, 6, 4, 6, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 4, 3, 6, 4, 6, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 4, 7, 2, 4, 9, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 4, 7, 2, 4, 9, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 8, 3, 2, 6, 6, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 8, 3, 2, 6, 6, 14);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 8, 7, 6, 6, 9, 10);
        }
        for (int i = 0; i < 10; i++) {
            dryModel(12, 8, 7, 6, 6, 9, 14);
        }
    }

    public static void verify() {
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 5, 4, 5, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(12, 6, 5, 4, 5, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 4, 5, 4, 5, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 7, 4, 5, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 5, 6, 5, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 5, 4, 6, 8, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 5, 4, 5, 10, 12);
        }
        for (int i = 0; i < 5; i++) {
            dryModel(10, 6, 5, 4, 5, 8, 9);
        }
    }

    public static void dryModel(double createDelay, double separateDelay, double jacketDelay, double pantsDelay, double assemblySuccessDelay, double assemblyFailureDelay, double customerSupportDelay) {
        CreateDry c = new CreateDry(createDelay);
        SuitsSeparatorProcess p1 = new SuitsSeparatorProcess(separateDelay);
        JacketProcess p2 = new JacketProcess(jacketDelay);
        PantsProcess p3 = new PantsProcess(pantsDelay);
        SuitAssemblyProcess p4 = new SuitAssemblyProcess(assemblySuccessDelay, assemblyFailureDelay);
        CustomerSupportProcess p5 = new CustomerSupportProcess(customerSupportDelay);

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
