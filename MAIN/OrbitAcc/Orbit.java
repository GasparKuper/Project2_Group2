package MAIN.OrbitAcc;

import MAIN.Body.PlanetBody;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.Vector3dInterface;

public class Orbit {

    public final static double G = 6.674e-11;

    /*
        ONE STEP TODO one step == one second

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

        Vector3d[] accAllPlanets = new Vector3d[11*10];
        int point = 0;
        for (PlanetBody body1 : planets) {
            for (PlanetBody body2 : planets) {
                if (body1 != body2) {
                    accAllPlanets[point++] = new Vector3d(AccX_Between(body1, body2), AccY_Between(body1, body2), AccZ_Between(body1, body2));
                }
            }
        }
        return accAllPlanets;
    }

    public static double AccX_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getX() - other.getPosition().getX())
                / Math.pow(other.getPosition().dist(one.getPosition()), 3));
    }

    public static double AccY_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getY() - other.getPosition().getY())
                / Math.pow(other.getPosition().dist(one.getPosition()), 3));
    }

    public static double AccZ_Between(PlanetBody one, PlanetBody other) {
        return (G * one.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
                / Math.pow(other.getPosition().dist(one.getPosition()), 3));
    }
}

