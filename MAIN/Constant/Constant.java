package MAIN.Constant;

import MAIN.Body.Vector3d;

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
    public static Vector3d[] VELOCITIES = {new Vector3d(26059.683719982604,-17025.303205517623,-551.8038048972293),
                                           new Vector3d(26064.95219309131,-17016.00144740622,-560.0979247429184),
                                           new Vector3d(26060.191835965557,-17025.529139641676,-551.626002550228),
                                           new Vector3d(26060.191835965557,-17025.529139641676,-551.626002550228)};

    /**
     * Array TITANLASTPOS contains the result of the calculation of the last position of titan for each solver
     * The values inside the array are used by the brute force
     */
    public static Vector3d[] TITANLASTPOS = {new Vector3d(8.790331863244542E11,-1.2037939117909973E12,-1.4405702426064991E10),
                                             new Vector3d(8.792057635699379E11,-1.2034125745659744E12,-1.4619852655912003E10),
                                             new Vector3d(8.790398852519586E11,-1.2038043949187737E12,-1.4401110492997408E10),
                                             new Vector3d(8.790398852519586E11,-1.2038043949187737E12,-1.4401110492997408E10)};
}
