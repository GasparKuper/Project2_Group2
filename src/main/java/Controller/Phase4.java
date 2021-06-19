package Controller;

import Body.SpaceCrafts.Lander;

import java.util.ArrayList;

public class Phase4 {

    private final double G = 1.352;

    public ArrayList<Lander> phase4(Lander state, double step){

        //Check angle of the lander
        double angleLander = state.getRotation();
        if(angleLander != 0.0 && angleLander != -0.0)
            throw new RuntimeException("Angle is not correct for Phase 2");

        //Check velocity rotation of the lander
        double velocityRotation = state.getRotationVelocity();
        if(velocityRotation != 0.0)
            throw new RuntimeException("Velocity rotation is not correct for Phase 2");


        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        double distance_half = state.getPosition().getY()/2.0;

        Integration update = new Integration();
        int point = 0;
        //Free fall on the half distance
        while (result.get(point).getPosition().getY() > distance_half) {
            Lander tmp = update.step(result.get(point++), 0, step);
            result.add(tmp);
        }

        double degreeInRadians = Math.toRadians(angleLander);
        double cos = Math.cos(degreeInRadians);

        //Distance from titan
        double distance = result.get(point).getPosition().getY();

        //Vy_currentState
        double Vy_last = result.get(point).getVelocity().getY();

        //Time needed for landing = || Distance from titan / (Vy_currentState * 0.5) ||
        double timeLanding = Math.abs(distance/(Vy_last * 0.5));

        //Acceleration_y = Vy_currentState / time needed for landing
        double y_Acceleration_ToSlowDown = Vy_last / timeLanding;

        //U = |Acceleration_y / cos(theta)| + G
        double u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;

        int iterationTime = (int) timeLanding;

        for (double i = 0; i < iterationTime; i+=step) {
            Lander tmp = update.step(result.get(point++), u_ToSlowDown, step);
            result.add(tmp);
            distance = result.get(point).getPosition().getY();

            Vy_last = result.get(point).getVelocity().getY();

            timeLanding = Math.abs(distance/(Vy_last * 0.5));

            y_Acceleration_ToSlowDown = Vy_last / timeLanding;

            u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;
        }

        //Final Step
        y_Acceleration_ToSlowDown = Vy_last / step;
        u_ToSlowDown = Math.abs(y_Acceleration_ToSlowDown / cos) + G;

        Lander tmp = update.step(result.get(point), u_ToSlowDown, step);
        result.add(tmp);


        return result;
    }
}
