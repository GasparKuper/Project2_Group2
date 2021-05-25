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
     * Gravitational constant
     */
    public final static double G = 6.67408e-11;

    /**
     * Step size used to calculate trajectories
     */
    public static double STEPSIZE = 500;

    /**
     * Final time used to calculate trajectories
     */
    public static double[] FINALTIME = {1.9628455E7,1.9640623E7,1.9630238E7,31556952};

    /**
     * thrust condition
     */
    public static boolean THRUST = false;

    /**
     * Array VELOCITIES contains the initial velocity of the probe computed by the brute force for each solver
     * The values inside the array are used to start the trajectory of the probe
     */
    public static Vector3dInterface[] VELOCITIES = {new Vector3d(45754.49514230483,-38810.39783267506,-527.8779302246595),
                                           new Vector3d(45653.49832969943,-38930.537266394975,-411.5923649919469),
                                           new Vector3d(45767.13717953718,-38795.42429374674,-532.7219902108546),
                                           new Vector3d(45767.13717953718,-38795.42429374674,-532.7219902108546)};

    /**
     * Array TITANLASTPOS contains the result of the calculation of the last position of titan for each solver
     * The values inside the array are used by the brute force
     */
    public static Vector3d[] TITANLASTPOS = {new Vector3d(8.792064226203754E11,-1.2040466447405679E12,-1.4284761255510523E10),
                                             new Vector3d(8.751845348489886E11,-1.2082007407347507E12,-1.1752216332025648E10),
                                             new Vector3d(8.792207314653517E11,-1.2042094620928135E12,-1.421029948775191E10),
                                             new Vector3d(8.790389926264305E11,-1.2038031270354197E12,-1.4401675088069757E10)};
    /**
     * Start position of the probe
     */
    public static final Vector3d STARTPOS = new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10, 8174296.311571818);
}
