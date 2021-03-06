package Controller;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;

import static Constant.Constant.WIND;

public class Integration {

    private final double G = 1.352;

    /**
     * Calculate double integral
     * @param state current state of the lander
     * @param u_mainThrust U = main engine
     * @param v_sideThrust V = side engines
     * @param step step size
     * @return new state of the lander
     */
    public Lander step(Lander state, double u_mainThrust, double v_sideThrust, double step){

        Lander lander = state.clone();

        //Deviation 20%
        Vector2d wind = state.generateRandomWind(step, 0.2);

        double inRadians = Math.toRadians(lander.getRotation());

        //TODO IMPORTANT: V(t+1) = V + V_old * stepsize + 1/2 * V_acc * stepsize^2
        Vector2d position = new Vector2d();
        position.setX(lander.getPosition().getX() + lander.getVelocity().getX() * step + (0.5 * u_mainThrust * Math.sin(inRadians)) * Math.pow(step, 2));

        position.setY(lander.getPosition().getY() + lander.getVelocity().getY() * step + (0.5 * u_mainThrust * Math.cos(inRadians) - G) * Math.pow(step, 2));

        //if(WIND)
        //    position = position.add(wind);

        lander.setPosition(position);

        Vector2d velocity = new Vector2d();
        velocity.setX(lander.getVelocity().getX() + (u_mainThrust * Math.sin(inRadians)) * step);

        velocity.setY(lander.getVelocity().getY() + (u_mainThrust * Math.cos(inRadians) - G) * step);

        if(WIND)
            velocity = velocity.add(wind);

        lander.setVelocity(velocity);

        lander.setRotation(lander.getRotation() + lander.getRotationVelocity() * step + (0.5 * v_sideThrust) * Math.pow(step, 2));

        lander.setRotationVelocity(lander.getRotationVelocity() + v_sideThrust * step);

        return lander;
    }
}
