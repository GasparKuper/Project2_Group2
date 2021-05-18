package ODESolver;

import Body.PlanetBody;
import Body.Rate;
import Body.State;
import Body.Vector3d;
import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
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

        RateInterface k1 = findK1(f,t,y);
        RateInterface k2 = findK2(f,t,h,y,k1);
        RateInterface k3 = findK3(f,t,h,y,k2);
        RateInterface k4 = findK4(f,t,h,y,k3);

        StateInterface state = ((State) y).clone();

        state = state.addMul(h/6, k1);
        state = state.addMul(2*h/6, k2);
        state = state.addMul(2*h/6, k3);
        state = state.addMul(h/6, k4);

        return state;
    }

    public RateInterface findK1(ODEFunctionInterface f, double t, StateInterface y) {
        StateInterface clone = ((State) y).clone();
        return f.call(t, clone);
    }

    public RateInterface findK2(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k1) {
        StateInterface clone = ((State) y).clone();
        return f.call(t + h/2, clone.addMul(1.0/2, k1));
    }

    public RateInterface findK3(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k2) {
        StateInterface clone = ((State) y).clone();
        return f.call(t + h/2, clone.addMul(1.0/2, k2));
    }

    public RateInterface findK4(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k3) {
        StateInterface clone = ((State) y).clone();
        return f.call(t + h, clone.addMul(1.0, k3));
    }
}
