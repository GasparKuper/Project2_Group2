package Body.Solvers;

import Body.SpaceCrafts.State;
import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;

/**
 * 4rd order Runge-Kutta
 */
public class RungeKutta {

    /**
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

        State state = ((State) y).clone();

        state = (State) new ImplicitEuler().step(h /6.0, k1, state);
        state = (State) new ImplicitEuler().step(2.0*h/6.0, k2, state);
        state = (State) new ImplicitEuler().step(2.0*h/6.0, k3, state);
        state = (State) new ImplicitEuler().step(h/6.0, k4, state);

        return state;
    }

    public RateInterface findK1(ODEFunctionInterface f, double t, StateInterface y) {
        State clone = ((State) y).clone();
        return f.call(t, clone);
    }

    public RateInterface findK2(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k1) {
        State clone = ((State) y).clone();
        return f.call(t + h/2, new ImplicitEuler().step(h * 0.5, k1, clone));
    }

    public RateInterface findK3(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k2) {
        State clone = ((State) y).clone();
        return f.call(t + h/2, new ImplicitEuler().step(h * 0.5, k2, clone));
    }

    public RateInterface findK4(ODEFunctionInterface f, double t, double h, StateInterface y, RateInterface k3) {
        State clone = ((State) y).clone();
        return f.call(t + h, new ImplicitEuler().step(h, k3, clone));
    }

}

