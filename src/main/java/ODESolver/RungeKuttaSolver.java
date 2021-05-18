package ODESolver;

import Body.PlanetBody;
import Body.Rate;
import Body.State;
import Body.Vector3d;
import Interfaces.ODEFunctionInterface;
import Interfaces.StateInterface;

import java.util.LinkedList;

public class RungeKuttaSolver {

    /*
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        StateInterface[] result = new State[ts.length + 1];

        result[0] = y0;

        double h = (ts[ts.length-1] - ts[0])/(ts.length-1);

        for (int i = 1; i <= ts.length; i++) {
            result[i] = step(f, ts[i-1], result[i-1], h);
        }

        return result;
    }

    /*
     * Solve the differential equation by taking multiple steps of equal size, starting at time 0.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   tf      the final time
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 including all intermediate states along the path
     */
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        int n = (int) (Math.ceil(tf/h) + 1);
        StateInterface[] result = new State[n];

        result[0] = y0;

        for (int i = 1; i <= n; i++) {
            result[i] = step(f, n * h, result[i-1], h);
        }

        return result;
    }

    /*
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        LinkedList<PlanetBody> solarSystem = ((State) y).celestialBody;
        LinkedList<Vector3d> ki = new LinkedList<>();

        for (int i = 0; i < solarSystem.size(); i++) {
            Rate rate = (Rate) f.call(t, y);
            Vector3d k = (Vector3d) rate.getAcceleration().get(i).mul(h);
            ki.add(k);
        }

        Rate k1 = new Rate(ki);
        ki.clear();

        for (int i = 0; i < solarSystem.size(); i++) {
            Rate rate = (Rate) f.call(t + h / 2, y.addMul(1.0/2, k1));
            Vector3d k = (Vector3d) rate.getAcceleration().get(i).mul(h);
            ki.add(k);
        }

        Rate k2 = new Rate(ki);
        ki.clear();

        for (int i = 0; i < solarSystem.size(); i++) {
            Rate rate = (Rate) f.call(t + h / 2, y.addMul(1.0/ 2, k2));
            Vector3d k = (Vector3d) rate.getAcceleration().get(i).mul(h);
            ki.add(k);
        }

        Rate k3 = new Rate(ki);
        ki.clear();

        for (int i = 0; i < solarSystem.size(); i++) {
            Rate rate = (Rate) f.call(t + h, y.addMul(1, k3));
            Vector3d k = (Vector3d) rate.getAcceleration().get(i).mul(h);
            ki.add(k);
        }

        Rate k4 = new Rate(ki);
        ki.clear();

        return y.addMul(1.0/6, k1).addMul(1.0/3, k2).addMul(1.0/3, k3).addMul(1.0/6, k4);
    }
}
