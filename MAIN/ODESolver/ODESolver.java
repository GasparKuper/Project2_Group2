package MAIN.ODESolver;

import MAIN.Body.*;
import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.ODESolverInterface;
import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;

/*
 * A class for solving a general differential equation dy/dt = f(t,y)
 *     y(t) describes the state of the system at time t
 *     f(t,y(t)) defines the derivative of y(t) with respect to time t
 */
public class ODESolver implements ODESolverInterface {

    /*
     * Solve the differential equation by taking multiple steps.
     *
     * @param   f       the function defining the differential equation dy/dt=f(t,y)
     * @param   y0      the starting state
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time
     * @return  an array of size ts.length with all intermediate states along the path
     */
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {

        StateInterface[] result = new State[ts.length+1];

        //Launching position
        result[0] = y0;

        for (int i = 0; i < ts.length; i++)
            result[i+1] = step(f, ts[i+1], result[i], ts[1]);


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
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {

        double [] ts = new double[(int) (tf/h)];

        for(int x = 0; x < ts.length; x++){
            ts[x] = h*x;
        }

        return solve(f, y0, ts );
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
    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {

        RateInterface velocity_acceleration = f.call(t, y);

        return y.addMul(h, velocity_acceleration);
    }
}
