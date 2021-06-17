package Run.CalculationForProbe;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;
import Controller.OpenLoopController;

import static Constant.Constant.STEPSIZE;

public class OpenLoopBruteForce {

    private Lander lander;
    private double minimumThrust = 0;
    private double maximumThrust = 3e7;

    public OpenLoopBruteForce(Lander lander) {
        this.lander = lander;
    }

    /**
     * Determines whether a landing on Titan is considered successful
     * @param lander lander at the final state of the landing on Titan
     * @return true if landing is safe
     */
    public boolean isSafeLanding(Lander lander) {
        if (Math.abs(lander.getPosition().getX()) > 0.1)
            return false;
        if (Math.abs(lander.getRotation() % (2 * Math.PI)) > 0.02)
            return false;
        if (Math.abs(lander.getVelocity().getX()) > 0.1)
            return false;
        if (Math.abs(lander.getVelocity().getY()) > 0.1)
            return false;
        if (Math.abs(lander.getRotationVelocity()) > 0.01)
            return false;

        return true;
    }

    /**
     * Finds thrust and torque to allow a lander to land safely on Titan
     * @return force of the thrust
     */
    public double findThrust() {
        OpenLoopController controller = new OpenLoopController();
        double t = 0;
        double force = (maximumThrust + minimumThrust) / 2;

        Lander testLander = null;
        while (testLander == null || testLander.getPosition().getY() > 0) {
            testLander = controller.step(t, force, lander);
            t = t + STEPSIZE;
        }

        if (isSafeLanding(testLander))
            return force;

        if (testLander.getVelocity().getY() > 0)
            maximumThrust = force;

        else minimumThrust = force;

        System.out.println("Velocity at landing: " + testLander.getVelocity().getY() + "\n" + "Thrust between: " + minimumThrust + " and " + maximumThrust);

        return findThrust();
    }

}