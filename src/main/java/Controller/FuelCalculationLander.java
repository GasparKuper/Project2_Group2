package Controller;

import Body.SpaceCrafts.Lander;

public class FuelCalculationLander {

    private final double mDotMax = 30e7;

    public void calculateFuel(Lander state, double step,  double u, double v){
        double total = Math.abs(u) + Math.abs(v);
        double fuelNeed = (total/mDotMax) * step;
        state.setFuel(state.getFuel() + fuelNeed);
    }
}
