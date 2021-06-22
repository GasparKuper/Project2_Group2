package Controller;

import Body.SpaceCrafts.Lander;

public class FuelCalculationLander {

    private final double mDotMax = 30e7;

    /**
     * Calculate how much fuel reqiure for the engine
     * @param state current state of the lander
     * @param step step size
     * @param u U - main engine
     * @param v V - side engines
     */
    public void calculateFuel(Lander state, double step,  double u, double v){
        double total = Math.abs(u) + Math.abs(v);
        double fuelNeed = (total/mDotMax) * step;
        state.setFuel(state.getFuel() + fuelNeed);
    }
}
