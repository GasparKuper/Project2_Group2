package MAIN.Body;

import MAIN.Interfaces.Vector3dInterface;

public class Orbit {

    public final static double G = 6.674e-11;

    /*
        ONE STEP TODO

        Acceleration == a
        Velocity == v
        Position == x
        Mass = m

        Force := G * m0*m / r^2

        TODO a := G * M0 * (x0-x) / r^3    TODO a:= Force/M  or a:= G * m0*m / (r^2) * m

        //New velocity of the object
        v := v + T * a  TODO  y(t) = a*t + b

        //New position of the object
        x := x + T * v  TODO  y(t) = a*t + b

     */

    public Vector3dInterface[] AccelerationVectorPlanet(PlanetBody[] planets) {

        Vector3d[] accAllPlanets = new Vector3d[10];
        int point = 0;
        for (PlanetBody body : planets) {
            if (body != planets[0]) {
                accAllPlanets[point++] = new Vector3d(AccX_Between(planets[0], body), AccY_Between(planets[0], body), AccZ_Between(planets[0], body));
            }
        }
        return accAllPlanets;
    }

    public static double AccX_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getX() - other.getPosition().getX())
                / Math.pow(one.getPosition().dist(other.getPosition()), 3));
    }

    public static double AccY_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getY() - other.getPosition().getY())
                / Math.pow(one.getPosition().dist(other.getPosition()), 3));
    }

    public static double AccZ_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
                / Math.pow(one.getPosition().dist(other.getPosition()), 3));
    }
}
