package Controller;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;

public class OpenLoopController {

    public static final double G = 1.352;

    /**
     * @param h step-size
     * @param thrust current acceleration provided by main thruster
     * @param torque current torque provided by side thrusters
     * @return movement of the probe at step t
     **/
    public Vector2d step(double h, Vector2d thrust, Vector2d torque, Lander lander) {

        double x = lander.getVelocity().getX() * lander.getRotation().getX();
        double y = lander.getVelocity().getY() * lander.getRotation().getY();
        double angle = (x + y)/(lander.getRotation().norm() * lander.getVelocity().norm());

        Vector2d acceleration = new Vector2d();
        acceleration.setX(thrust.getX() * Math.sin(angle));
        acceleration.setY(thrust.getY() * Math.cos(angle) - G);

        lander.setVelocity(lander.getVelocity().addMul(h, acceleration));

        lander.setRotationVelocity(lander.getRotationVelocity().addMul(h, torque));

        lander.setPosition(lander.getPosition().addMul(h, lander.getVelocity()));

        lander.setRotation(lander.getRotation().addMul(h, lander.getRotationVelocity()));

        return lander.getPosition();
    }

    // TODO: Set fuel
    public Lander createLander(Vector3d posTitan, Vector3d posProbe) {
        Vector2d position = new Vector2d();
        position.setX(0);
        position.setY(posTitan.dist(posProbe));

        Vector2d velocity = new Vector2d();
        velocity.setX(0);
        velocity.setY(0);

        Vector2d rotation = new Vector2d();
        rotation.setX(0);
        rotation.setY(1);

        Vector2d rotationVelocity = new Vector2d();
        rotationVelocity.setX(0);
        rotationVelocity.setY(0);

        Lander lander = new Lander(position, velocity, 6000, 0, rotation, rotationVelocity);

        return lander;
    }
}
