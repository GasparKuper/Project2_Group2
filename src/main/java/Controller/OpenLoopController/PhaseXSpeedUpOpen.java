package Controller.OpenLoopController;

import Body.SpaceCrafts.Lander;
import Controller.FuelCalculationLander;
import Controller.Integration;

import java.util.ArrayList;

public class PhaseXSpeedUpOpen {

    /**
     *
     * @param state The state of the lander
     * @param time time to reach the max Velocity for the lander
     * @return Trajectory of the lander
     */
    public ArrayList<Lander> phaseSpeedUp(Lander state, double time, double step){


        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();

        double tmp1 = time - 120.0;

        //Distance to x = 0
        double distance = state.getPosition().getX();

        // Vx_max = ||distance / time||
        double Vx_max = Math.abs((distance/time));

        //Sin
        double angleLander = state.getRotation();
        double degreeInRadians = Math.toRadians(angleLander);
        double sin = Math.sin(degreeInRadians);

        // Acceleration_x = Vx_max / time
        double x_Acceleration = ((Vx_max / tmp1));

        //U = ||Acceleration_x / sin(theta)||
        double u = Math.abs(x_Acceleration/sin);

        //todo Speed up Velocity X
        int point = 0;
        for (double i = 0; i < tmp1; i+=step) {
            Lander tmp = update.step(result.get(point++), u, 0, step);
            result.add(tmp);
            //Fuel
            fuel.calculateFuel(result.get(point), step, u, 0);
        }

        return result;
    }

}
