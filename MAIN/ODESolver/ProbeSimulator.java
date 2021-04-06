package MAIN.ODESolver;

import MAIN.Body.Data;
import MAIN.Body.PlanetBody;
import MAIN.Body.State;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.ProbeSimulatorInterface;
import MAIN.Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class ProbeSimulator implements ProbeSimulatorInterface {

    private State[] trajectory;

    /*
     * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
     *
     * @param   p0      the starting position of the probe, relative to the earth's position.
     * @param   v0      the starting velocity of the probe, relative to the earth's velocity.
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
     * @return  an array of size ts.length giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre.
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {

        Vector3d[] probeTraj = new Vector3d[ts.length + 1];

        Data data = new Data();

        ODESolver odeSolver = new ODESolver();

        ODEFunction odeFunction = new ODEFunction();

        LinkedList<PlanetBody> solarSystem = data.getPlanets();

        State launchPosition = new State(15000, p0, v0, solarSystem, true);

        //Array with positions and velocities of the probe and planets.
        trajectory = (State[]) odeSolver.solve(odeFunction, launchPosition, ts[ts.length-1], ts[0]);

        System.out.println("DONE");

        for (int i = 0; i < trajectory.length; i++)
            probeTraj[i] = (Vector3d) trajectory[i].position;

        return probeTraj;
    }

    /*
     * Simulate the solar system with steps of an equal size.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
     *
     * @param   tf      the final time of the evolution.
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        double tsLength = tf/h;
        if(tf % h > 0.0)
            tsLength++;
        double [] ts = new double[(int) (tsLength)];

        //Calculate How many steps we have
        for(int x = 1; x < ts.length; x++){
                ts[x-1] = h * x;
        }

        ts[ts.length-1] = tf;

        return trajectory(p0, v0, ts);
    }

    public State[] getTrajectory() {
        return trajectory;
    }

}
