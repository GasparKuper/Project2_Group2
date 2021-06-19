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

        Lander lastState = state;
        //angle of the lander
        double angleLander = lastState.getRotation();

        ArrayList<Lander> result = new ArrayList<>();
        result.add(lastState);

        //Distance to x = 0
        double distance = lastState.getPosition().getX();

        // Vx_max = ||distance / time||
        double Vx_max = 2.0 * Math.abs((distance/time));

        double degreeInRadians = Math.toRadians(angleLander);
        double sin = Math.sin(degreeInRadians);

        // Acceleration_x = Vx_max / time
        double x_Acceleration = ((Vx_max / time));

        //U = ||Acceleration_x / sin(theta)||
        double u = Math.abs(x_Acceleration/sin);

        Integration update = new Integration();

        int point = 0;
        for (double i = 0; i < time; i+=step) {
            Lander tmp = update.step(result.get(point++), u, 0, step);
            result.add(tmp);

            lastState = result.get(point);
            angleLander = lastState.getRotation();

            //Distance to x = 0
            distance = lastState.getPosition().getX();

            // Vx_max = ||distance / time||
            Vx_max = 2.0 * Math.abs((distance/time));

            // Acceleration_x = Vx_max / time
            x_Acceleration = ((Vx_max / time));

            degreeInRadians = Math.toRadians(angleLander);
            sin = Math.sin(degreeInRadians);
            //U = ||Acceleration_x / sin(theta)||
            u = Math.abs(x_Acceleration/sin);
        }

        lastState = result.get(point);
        distance = lastState.getPosition().getX();
        //Time to slow down the lander = ||distance / Vx_currentVelocity||
        double timeToSlowDown = Math.abs(distance/(lastState.getVelocity().getX() * 0.5));

        //Acceleration_x to slow down the lander = -Vx_currentVelocity / Time to slow down the lander
        double x_Acceleration_ToSlowDown = (-lastState.getVelocity().getX()/timeToSlowDown) ;

        angleLander = lastState.getRotation();
        degreeInRadians = Math.toRadians(angleLander);
        sin = Math.sin(degreeInRadians);
        //U to slow down the lander = Acceleration_x to slow down the lander / sin(theta)
        double u_ToSlowDown = x_Acceleration_ToSlowDown/sin;

        double iterationTime = timeToSlowDown;

        double remainTime = timeToSlowDown % step;

        for (double i = 0; i < iterationTime; i+=step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, 0, step);
            result.add(tmp);

            lastState = result.get(point);
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

        //Final Step
        x_Acceleration_ToSlowDown = -lastState.getVelocity().getX() / remainTime;
        u_ToSlowDown = Math.abs(x_Acceleration_ToSlowDown / sin);

        Lander tmp = update.step(result.get(point), u_ToSlowDown, 0, remainTime);
        result.add(tmp);


        return result;
    }

}
