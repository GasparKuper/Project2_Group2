package Controller;

import Body.SpaceCrafts.Lander;

import java.util.ArrayList;

public class Phase2 {

    /**
     *
     * @param state The state of the lander
     * @param time time to reach the max Velocity for the lander
     * @return Trajectory of the lander
     */
    public ArrayList<Lander> phase2(Lander state, double time, double step){

        //Check angle of the lander
        double angleLander = state.getRotation();
        if(angleLander != 90.0 && angleLander != -90.0)
            throw new RuntimeException("Angle is not correct for Phase 2");

        //Check velocity rotation of the lander
        double velocityRotation = state.getRotationVelocity();
        if(velocityRotation != 0.0)
            throw new RuntimeException("Velocity rotation is not correct for Phase 2");

        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        //Distance to x = 0
        double distance = state.getPosition().getX();

        // Vx_max = ||distance / time||
        double Vx_max = Math.abs((distance/time));

        // Acceleration_x = Vx_max / time
        double x_Acceleration = Vx_max / time;

        double degreeInRadians = Math.toRadians(angleLander);
        double sin = Math.sin(degreeInRadians);

        //U = ||Acceleration_x / sin(theta)||
        double u = Math.abs(x_Acceleration/sin);

        Integration update = new Integration();

        int point = 0;
        for (double i = 0; i < time; i+=step) {
            Lander tmp = update.step(result.get(point++), u, step);
            result.add(tmp);
        }

        Lander lastState = result.get(point);

        //Time to slow down the lander = ||distance / Vx_currentVelocity||
        double timeToSlowDown = Math.abs(distance/(lastState.getVelocity().getX()));

        //Acceleration_x to slow down the lander = -Vx_currentVelocity / Time to slow down the lander
        double x_Acceleration_ToSlowDown = (-lastState.getVelocity().getX()/timeToSlowDown);

        //U to slow down the lander = Acceleration_x to slow down the lander / sin(theta)
        double u_ToSlowDown = x_Acceleration_ToSlowDown / sin;

        double remainTime = timeToSlowDown % step;

        int iterationTime = (int) timeToSlowDown;

        for (double i = 0; i < iterationTime; i+=step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, step);
            result.add(tmp);
        }

        //Final iteration
        if(result.get(point).getPosition().getX() > 0.001) {
            Lander tmp = update.step(result.get(point), u_ToSlowDown, remainTime);
            result.add(tmp);
        }

        return result;
    }

}
