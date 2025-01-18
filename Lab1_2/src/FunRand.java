import java.util.Random;

public class FunRand {
    static Random rand = new Random();
    public static double Exp(double timeMean) {
        double a = 0;
        while (a == 0) {
            //a = Math.random();
            a = rand.nextDouble();
        }
        a = -timeMean * Math.log(a);
        return a;
    }

    public static double Unif(double timeMin, double timeMax) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = timeMin + a * (timeMax - timeMin);
        return a;
    }

    public static double Norm(double timeMean, double timeDeviation) {
        double a;
        Random r = new Random();
        a = timeMean + timeDeviation * r.nextGaussian();
        return a;
    }
}
