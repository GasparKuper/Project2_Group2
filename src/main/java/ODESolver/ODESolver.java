package ODESolver;

import Body.PlanetBody;
import Body.State;
import Interfaces.ODEFunctionInterface;
import Interfaces.ODESolverInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;

import java.util.LinkedList;

import static Constant.Constant.SOLVER;

/**
 * A class for solving a general differential equation dy/dt = f(t,y)
 *     y(t) describes the state of the system at time t
 *     f(t,y(t)) defines the derivative of y(t) with respect to time t
 */
public class ODESolver implements ODESolverInterface {


    LinkedList<PlanetBody> solarSystem;


    /**
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

        for (int i = 0; i < ts.length; i++) {

            solarSystem = ((State) y0).celestialBody;


            if (i == ts.length - 1 && i != 0) {
                result[i + 1] = step(f, ts[i], result[i], ts[i] - ts[i-1]);
            } else {
                result[i + 1] = step(f, ts[i], result[i], ts[0]);
            }
        }

        return result;
    }

    /**
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

        double tsLength = tf/h;
        if(tf % h > 0.0)
            tsLength++;
        double [] ts = new double[(int) tsLength];

        for(int x = 1; x < ts.length; x++){
            ts[x-1] = h * x;
        }

        ts[ts.length-1] = tf;

        return solve(f, y0, ts);
    }


    private boolean stormerFlag = true;

    /**
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

        RateInterface velocity_acceleration = f.call(h, y);

        State x = (State) y;

        //Create a clone of the State object
        State clone = x.clone();

        if(SOLVER == 1) //Symplectic Euler Method
            clone = (State) clone.addMul(h, velocity_acceleration);
        else if(SOLVER == 2) //Implicit Euler Solver
            clone = (State) clone.addMulImplicit(h, velocity_acceleration);
        else if(SOLVER == 3) //Velocity-Verlet Solver
            clone = (State) clone.addMulVerletVelocity(h, velocity_acceleration, f);
        else if(SOLVER == 4) { //Stormer-Verlet Solver
            clone = (State) clone.addMulVerletStormer(h, velocity_acceleration, f);
        }

        //Return a new state with a new position and velocity
        return clone;
    }
}