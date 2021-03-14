package NWGravity;

public class LaunchMain {

    private final static double G = 6.674e-11;

    public static void main(String[] args) {

        Data data = new Data();

        PlanetBody[] object = data.SolarSystem();

        double[] totalForce = new double[object.length * (object.length-1)];

        /*
        Probably not correct
        for (int i = 1; i < object.length; i++) {
            totalForce[i-1] = force(object[0], object[i]);
        }
         */

        int point = 0;
        for (PlanetBody body1 : object) {
            for (PlanetBody body2 : object) {
                if (body1 != body2) {
                    totalForce[point++] = force(body1, body2);
                }
            }
        }
    }

    private static double force(PlanetBody one, PlanetBody other){
        return (G * one.getM() * other.getM() / Math.pow(one.getVector().dist(other.getVector()), 2));
    }
}
