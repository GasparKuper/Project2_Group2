package Controller;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;

import static Constant.Constant.STEPSIZE;

public class Integration {

    private final double G = 1.352;

    public Lander step(Lander state, double u_mainThrust, double step){

        Lander lander = state.clone();

        double inRadians = Math.toRadians(lander.getRotation());

        //TODO IMPORTANT: V(t+1) = V + V_old * stepsize + 1/2 * V_acc * stepsize^2
        Vector2d position = new Vector2d();
        position.setX(lander.getPosition().getX() + lander.getVelocity().getX() * step + (0.5 * u_mainThrust * Math.sin(inRadians)) * Math.pow(step, 2));

        position.setY(lander.getPosition().getY() + lander.getVelocity().getY() * step + (0.5 * u_mainThrust * Math.cos(inRadians) - G) * Math.pow(step, 2));

        lander.setPosition(position);

        Vector2d velocity = new Vector2d();
        velocity.setX(lander.getVelocity().getX() + (u_mainThrust * Math.sin(inRadians)) * step);

        velocity.setY(lander.getVelocity().getY() + (u_mainThrust * Math.cos(inRadians) - G) * step);

        lander.setVelocity(velocity);

        return lander;
    }
}
