package Controller;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;

import java.util.ArrayList;

public class Phase4 {

    private final double G = 1.352;

    public ArrayList<Lander> phase4(Lander state, double step){

        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        double distance_half = state.getPosition().getY()/2.0;

        //todo What you know about rollin' down in the deep?
        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();
        int point = 0;
        //Free fall on the half distance
        if(distance_half > 50000.0) {
            while (result.get(point).getPosition().getY() > distance_half) {
                Lander tmp = update.step(result.get(point++), 0, 0, step);
                result.add(tmp);
            }
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

        double iterationTime = timeLanding;

        for (double i = 0; i < iterationTime && lastState.getVelocity().getY() < 0.0; i+=step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, 0, step);
            result.add(tmp);

            lastState = result.get(point);

            //Feul
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

        if (lastState.getVelocity().getY() > 0 && lastState.getVelocity().getY() < G*2)
            lastState.setVelocity(new Vector2d(lastState.getVelocity().getX(), 0));

        return result;
    }
}
