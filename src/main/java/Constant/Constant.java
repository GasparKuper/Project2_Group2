package Constant;

import Body.Vector3d;

public class Constant {

    /**
     * Variable SOLVER decides which solver, we will use in our program.
     * 1 = Symplectic Euler
     * 2 = Implicit Euler
     * 3 = Velocity-Verlet
     * 4 = Stormer-Velocity
     * 5 = 4rd order Runge-Kutta
     */
    public static int SOLVER = 3;

    /**
     * Array VELOCITIES contains the initial velocity of the probe computed by the brute force for each solver
     * The values inside the array are used to start the trajectory of the probe
     */
    public static Vector3d[] VELOCITIES = {new Vector3d(26059.64822902804,-17025.274251441046,-551.8266716533692),
                                           new Vector3d(26064.922970305346,-17015.98491274584,-560.1116360658285),
                                           new Vector3d(26060.15681226142,-17025.499984899376,-551.6491230911201),
                                           new Vector3d(26060.15681226142,-17025.499984899376,-551.6491230911201)};

    /**
     * Array TITANLASTPOS contains the result of the calculation of the last position of titan for each solver
     * The values inside the array are used by the brute force
     */
    public static Vector3d[] TITANLASTPOS = {new Vector3d(8.790322792822222E11,-1.2037926536678767E12,-1.4406260567073011E10),
                                             new Vector3d(8.792050124292278E11,-1.203411806119769E12,-1.4620174774740269E10),
                                             new Vector3d(8.790389926264305E11,-1.2038031270354197E12,-1.4401675088069757E10),
                                             new Vector3d(8.790389926264305E11,-1.2038031270354197E12,-1.4401675088069757E10)};
}
