package Constant;

import Body.Vector3d;
import Interfaces.Vector3dInterface;

public class Constant {

    /**
     * Variable SOLVER decides which solver, we will use in our program.
     * 1 = Symplectic Euler
     * 2 = Implicit Euler
     * 3 = Velocity-Verlet
     * 4 = 4rd order Runge-Kutta
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
    public static Vector3dInterface[] VELOCITIES = {new Vector3d(45754.49514230483,-38810.39783267506,-527.8779302246595),
                                           new Vector3d(45653.49832969943,-38930.537266394975,-411.5923649919469),
                                           new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666),
                                           new Vector3d(45767.13717953718,-38795.42429374674,-532.7219902108546)};

    /**
     * Start position of the probe
     */
    public static final Vector3d STARTPOS = new Vector3d(4301000.0,-4692000.0,-276000.0);

    /**
     *
     */
    public static double EXHAUSTSPEED = 20000;

}