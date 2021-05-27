package Constant;

import Body.Vector3d;
import Interfaces.Vector3dInterface;

/**
 * Constant for the program
 */
public class Constant {

    /**
     * Variable SOLVER decides which solver, we will use in our program.
     * 1 = Symplectic Euler
     * 2 = Implicit Euler
     * 3 = Velocity-Verlet
     * 4 = 4th order Runge-Kutta
     */
    public static int SOLVER = 3;

    /**
     * Flag for Verlet JUnit tests
     */
    public static boolean FLAG_VERLET_TEST = false;

    /**
     * Gravitational constant
     */
    public final static double G = 6.67408e-11;

    /**
     * Step size used to calculate trajectories
     */
    public static double STEPSIZE = 600;

    /**
     * Final time used to calculate trajectories
     */
    public static double[] FINALTIME = {3.154e+7, 3.154e+7, 6.167E7, 3.154e+7};

    /**
     * Thrust condition
     */
    public static boolean THRUST = false;

    /**
     * Fuel for the probe
     */
    public static double FUEL = 99900;

    /**
     * Array VELOCITIES contains the initial velocity of the probe computed by the brute force for each solver
     * The values inside the array are used to start the trajectory of the probe
     */
    public static Vector3dInterface[] VELOCITIES = {new Vector3d(21879.822207944835,-32073.987587165077,-1064.792031059488),
                                           new Vector3d(21744.742783895097,-31956.466390002974,-1113.868596091232),
                                           new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666),
                                           new Vector3d(19892.578125,-29904.78515625,-935.05859375)};

    /**
     * Start position of the probe
     */
    public static final Vector3d STARTPOS = new Vector3d(4301000.0,-4692000.0,-276000.0);

    /**
     *
     */
    public static double EXHAUSTSPEED = 20000;

}