package MAIN.ODESolver;

import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;

public class ODEFunction implements ODEFunctionInterface {
    @Override
    public RateInterface call(double t, StateInterface y) {
        return null;
    }
}
