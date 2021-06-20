package Controller.CloseLoopController;

import Body.SpaceCrafts.Lander;
import Controller.FuelCalculationLander;
import Controller.Integration;

import java.util.ArrayList;

public class PhaseLanding {

    private final double G = 1.352;

    public ArrayList<Lander> phaseLanding(Lander state, double step){

        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        //todo What you know about rollin' down in the deep?
        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();
        int point = 0;
        //Free fall on the half distance
            while (result.get(point).getPosition().getY() > 50000.0) {
                Lander tmp = update.step(result.get(point++), 0, 0, step);
                result.add(tmp);
            }

        //todo Slow down Velocity Y
        Lander lastState = result.get(point);

        //Distance from titan
        double distance = lastState.getPosition().getY();

        //Vy_currentState
        double Vy_last = lastState.getVelocity().getY();

        //Time needed for landing = || Distance from titan / (Vy_currentState * 0.5) ||
        double timeLanding = Math.abs(distance/(Vy_last * 0.5));

        //Acceleration_y = Vy_currentState / time needed for landing
        double y_Acceleration_ToSlowDown = Vy_last / timeLanding;

        //Cos
        double angleLander = lastState.getRotation();
        double degreeInRadians = Math.toRadians(angleLander);
        double cos = Math.cos(degreeInRadians);

        //U = |Acceleration_y / cos(theta)| + G
        double u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;

        while (timeLanding >= step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, 0, step);
            result.add(tmp);

            lastState = result.get(point);

            //Fuel
            fuel.calculateFuel(result.get(point), step,u_ToSlowDown, 0);

            //Distance from titan
            distance = lastState.getPosition().getY();

            //Vy_currentState
            Vy_last = lastState.getVelocity().getY();

            //Time needed for landing = || Distance from titan / (Vy_currentState * 0.5) ||
            timeLanding = Math.abs(distance/(Vy_last * 0.5));

            //Acceleration_y = Vy_currentState / time needed for landing
            y_Acceleration_ToSlowDown = Vy_last / timeLanding;

            //Cos
            angleLander = lastState.getRotation();
            degreeInRadians = Math.toRadians(angleLander);
            cos = Math.cos(degreeInRadians);

            //U = |Acceleration_y / cos(theta)| + G
            u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;
        }

        double remainTime = timeLanding % step;

        //Final Step
        y_Acceleration_ToSlowDown = Vy_last / remainTime;
        u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;

        Lander tmp = update.step(result.get(point), u_ToSlowDown, 0, remainTime);
        result.add(tmp);

        return result;
    }
}
