package Controller.CloseLoopController;

import Body.SpaceCrafts.Lander;
import Controller.FuelCalculationLander;
import Controller.Integration;

import java.util.ArrayList;

public class PhaseXSpeedUpClose {

    /**
     * Speed up the lander
     * @param step step size
     * @param state The state of the lander
     * @param timeInput time to reach the max Velocity for the lander
     * @return Trajectory of the lander
     */
    public ArrayList<Lander> phaseSpeedUp(Lander state, double timeInput, double step){

        double time = timeInput + 23.0;
        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();

        //todo Speed up Velocity X
        int point = 0;
        for (double i = 0; i < time; i+=step) {
            Lander lastState = result.get(point);
            //Distance to x = 0
            double distance = lastState.getPosition().getX();

            // Vx_max = ||distance / time||
            double Vx_max = Math.abs((distance/time));

            //Sin
            double angleLander = lastState.getRotation();
            double degreeInRadians = Math.toRadians(angleLander);
            double sin = Math.sin(degreeInRadians);

            // Acceleration_x = Vx_max / time
            double x_Acceleration = ((Vx_max / time));

            //U = ||Acceleration_x / sin(theta)||
            double u = Math.abs(x_Acceleration/sin);

            Lander tmp = update.step(result.get(point++), u, 0, step);
            result.add(tmp);

            //Fuel
            fuel.calculateFuel(result.get(point), step, u, 0);
        }

        return result;
    }

}
