package Controller.CloseLoopController;

import Body.SpaceCrafts.Lander;
import Controller.FuelCalculationLander;
import Controller.Integration;

import java.util.ArrayList;

public class PhaseXSlowDownClose {

    /**
     *
     * @param state The state of the lander
     * @return Trajectory of the lander
     */
    public ArrayList<Lander> phaseToSlowDownX(Lander state, double step){


        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();
        //todo Slow down Velocity X

        Lander lastState = state;
        double distance = lastState.getPosition().getX();
        //Time to slow down the lander = ||distance / Vx_currentVelocity||
        double timeToSlowDown = Math.abs(distance/(lastState.getVelocity().getX() * 0.5));

        //Acceleration_x to slow down the lander = -Vx_currentVelocity / Time to slow down the lander
        double x_Acceleration_ToSlowDown = (lastState.getVelocity().getX()/timeToSlowDown) ;

        //Sin
        double angleLander = lastState.getRotation();
        double degreeInRadians = Math.toRadians(angleLander);
        double sin = Math.sin(degreeInRadians);

        //U to slow down the lander = Acceleration_x to slow down the lander / sin(theta)
        double u_ToSlowDown = x_Acceleration_ToSlowDown/sin;


        int point = 0;
        while (timeToSlowDown >= step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, 0, step);
            result.add(tmp);

            lastState = result.get(point);
            //Fuel Calculate
            fuel.calculateFuel(result.get(point), step,u_ToSlowDown, 0);

            angleLander = lastState.getRotation();

            distance = lastState.getPosition().getX();
            //Time to slow down the lander = ||distance / Vx_currentVelocity||
            timeToSlowDown = Math.abs(distance/(lastState.getVelocity().getX() * 0.5));

            //Acceleration_x to slow down the lander = -Vx_currentVelocity / Time to slow down the lander
            x_Acceleration_ToSlowDown = (-lastState.getVelocity().getX()/timeToSlowDown);

            degreeInRadians = Math.toRadians(angleLander);
            sin = Math.sin(degreeInRadians);
            //U to slow down the lander = Acceleration_x to slow down the lander / sin(theta)
            u_ToSlowDown = x_Acceleration_ToSlowDown/sin;
        }

        double remainTime = timeToSlowDown % step;

        //Final Step
        x_Acceleration_ToSlowDown = -lastState.getVelocity().getX() / remainTime;
        u_ToSlowDown = Math.abs(x_Acceleration_ToSlowDown / sin);

        Lander tmp = update.step(result.get(point), u_ToSlowDown, 0, remainTime);
        result.add(tmp);


        return result;
    }
}
