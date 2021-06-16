package Run.CalculationForProbe;

import Body.Planets.Data;
import Body.Planets.PlanetBody;
import Body.Vector.Vector3d;
import Interfaces.Vector3dInterface;

import java.util.LinkedList;
import static Constant.Constant.FUEL;
import static Constant.Constant.STEPSIZE;

public class FuelCalculation {

    private final double EXHAUST_VELOCITY = 2e4;
    private final double mDotMax = 30e7 / EXHAUST_VELOCITY;

    /**
     * Calculate how many fuel we need
     * @param initialVelocity Velocity, which we want to reach
     * @param velocityPlanet Velocity of the object
     * @param step Step size, which solvers use
     * @param massProbe Mass of the probe with lander (and fuel depends on situation)
     * @return mass of fuel
     */
    public double findMassFuel(Vector3dInterface initialVelocity, Vector3dInterface velocityPlanet, double step, double massProbe){
        Vector3dInterface force = initialVelocity.sub(velocityPlanet).mul(massProbe/step);
        double mDot = force.norm()/EXHAUST_VELOCITY;
        double time = (mDot/mDotMax) * step;
        return time * mDotMax;
    }

    public static void main(String[] args) {

        Data data = new Data();
        LinkedList<PlanetBody> planets = data.getPlanets();
        final double MASS_PROBE = 78000+6000;


        FUEL = new FuelCalculation().findMassFuel(new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666),
                planets.get(3).getVelocity(), STEPSIZE, MASS_PROBE);

    }
}
