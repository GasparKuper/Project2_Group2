package MAIN.Solver;

import MAIN.Interfaces.FunctionInterface;
import MAIN.Interfaces.Vector3dInterface;

public class Function implements FunctionInterface {

    /*
     * This is the function that represents the first derivative
     * denoted as f in your project manual. You need to implement
     * this function according to the laws of physics.
     *
     * For example, consider the state of the system at time t as
     * this linear function,
     * y(t) = a*t + b,
     * then we can choose any function f whose derivative is a.
     * The simplest such f is
     * f(t, y(t)) = a.
     *
     * @param   t   the time at which to evaluate the function
     * @param   s   the location at which to evaluate the function
     */
    @Override
    public Vector3dInterface call(double t, Vector3dInterface s) {
        return null;
    }

}
