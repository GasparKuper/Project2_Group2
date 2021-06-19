package Controller;

import Body.SpaceCrafts.Lander;

import java.util.ArrayList;

public class RotationPhase {

    public ArrayList<Lander> rotationPhase(Lander state, double time, double step, double thetaRequire){

        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        Integration update = new Integration();
        FuelCalculationLander fuel = new FuelCalculationLander();

        //todo Speed up the angle velocity
        int point = 0;
        for (double i = 0; i < time; i+=step) {
            Lander lastState = result.get(point);

            //Angle
            double angleLander = lastState.getRotation();

            //Angle Velocity
            double angleVelocity = lastState.getRotationVelocity();

            //V_maxRotation = 2.0 * ((0.5 * (theta_require - current_angle)/time) - angle velocity)
            double max_angleVelocity = 2.0 * (0.5 * (((thetaRequire - angleLander)/time)) - angleVelocity);

            //V = V_maxRotation / time
            double v = max_angleVelocity/time;

            Lander tmp = update.step(result.get(point++), 0, v, step);
            result.add(tmp);
            fuel.calculateFuel(result.get(point), step, 0, v);
        }

        //todo Slow down the angle velocity

        Lander lastState = result.get(point);

        //Angle
        double angleLander = lastState.getRotation();

        //Angle Velocity
        double angleVelocity = lastState.getRotationVelocity();

        double tmp_theta = thetaRequire - angleLander;

        //Time needed to slow down = ||(theta_require - current_angle)/(Angle Velocity_current * 0.5)
        double timeToSlowDown = Math.abs(tmp_theta/(angleVelocity * 0.5));

        //V to slow down the lander = Angle Velocity_current / Time needed to slow down
        double v_ToSlowDown = -angleVelocity / timeToSlowDown;

        double iterationTime = timeToSlowDown;

        double remainTime = timeToSlowDown % step;

        for (double i = 0; i < iterationTime; i+=step) {
            Lander tmp = update.step(result.get(point++), 0, v_ToSlowDown, step);
            result.add(tmp);

            lastState = result.get(point);
            //Fuel
            fuel.calculateFuel(lastState, step, 0, v_ToSlowDown);

            //Angle
            angleLander = lastState.getRotation();

            //Angle Velocity
            angleVelocity = lastState.getRotationVelocity();

            tmp_theta = thetaRequire - angleLander;

            //Time needed to slow down = ||(theta_require - current_angle)/(Angle Velocity_current * 0.5)
            timeToSlowDown = Math.abs(tmp_theta/(angleVelocity * 0.5));

            //V to slow down the lander = Angle Velocity_current / Time needed to slow down
            v_ToSlowDown = -angleVelocity / timeToSlowDown;
        }

        //Final Step
        //V to slow down the lander = Angle Velocity_current / Time needed to slow down
        v_ToSlowDown = -angleVelocity / remainTime;

        Lander tmp = update.step(result.get(point), 0, v_ToSlowDown, remainTime);
        result.add(tmp);


        return result;
    }
}
