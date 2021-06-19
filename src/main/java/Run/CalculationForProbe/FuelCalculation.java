package Run.CalculationForProbe;

import Body.Planets.Data;
import Body.Planets.PlanetBody;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.MissionProbe;

import java.util.LinkedList;

import static Constant.Constant.*;

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
        MissionProbe probe = new MissionProbe();

        State[] state = probe.trajectoryProbeCalculationToTitan();

        State[] state2 = probe.trajectoryProbeCalculationOrbitTitan(state[state.length - 1]);

        FuelCalculation fuel = new FuelCalculation();
        double FuelNeeded = 0;

        FuelNeeded += fuel.findMassFuel(new Vector3d(-19457.51190185663,8694.88542609827800331,1332.7261805534363),
                state2[state2.length - 1].velocity, 600, 78000);

        FuelNeeded += fuel.findMassFuel(new Vector3d(-1365.4485587266402,1117.4249977106176,0.0),
                state[state.length - 1].velocity, 60, 78000+FuelNeeded+6000);

        FuelNeeded += fuel.findMassFuel(new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666),
                planets.get(3).getVelocity(), 600, 78000+FuelNeeded+6000);

        System.out.println("Fuel need for this mission = " + FuelNeeded);

    }
}
