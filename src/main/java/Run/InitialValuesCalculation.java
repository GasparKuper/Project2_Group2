package Run;

import Body.State;
import Body.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;

import static Constant.Constant.*;

/**
 * InitialValuesCalculation class calculates the initial velocity of the probe and the final time at which we reach titan using a bruteforce
 *
 */
public class InitialValuesCalculation {
    private static double minDist = Double.MAX_VALUE;
    private static Vector3d lastPos;
    private Vector3dInterface initialVelocity;
    private Vector3d testDist;
    private static Vector3dInterface bruteForceResult;
    private static State[] trajectoryOfAll;

    /**
     * Method used to run the bruteforce and display the results for each solver
     */
    public static void main(String[] args) {
        InitialValuesCalculation init = new InitialValuesCalculation();
        boolean end = false;
        // init.computeBruteforce(3);
        for (int i = 3; i < 4; i++) {
        System.out.println("We reach titan at " +init.timeBruteForce(i+1));
        init.precisionBruteforce(i+1);
        System.out.println("With initial speed of " +VELOCITIES[i].norm());
        System.out.println("And initial velocity is "+VELOCITIES[i].toString());
        System.out.println("Distance to titan is : "+calculateTraj(VELOCITIES[i],i+1).dist(lastPos));
        System.out.println("Last pos probe : "+calculateTraj(VELOCITIES[i],i+1));
        System.out.println("Last pos titan "+lastPos);
        }
    }

    /**
     * finds the initial velocity of the probe
     * @param solverNbr integer that decides which solver we will use in our calculations
     * 1 = Symplectic Euler
     * 2 = Implicit Euler
     * 3 = Velocity-Verlet
     * 4 = 4th order Runge-Kutta
     * @param precision is the minimal step that we use
     * @return the initial velocity
     */
    public Vector3dInterface computeBruteforce(int solverNbr,double precision){
        int total = 0;
        float step = 10000;
        Vector3d previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

        initialVelocity = new Vector3d(0,0,0.0);
        Vector3d result = calculateTraj(initialVelocity, solverNbr);
        Vector3d lastPosTitan = getLastPos();
        minDist = result.dist(lastPosTitan);
        while (step > precision) {
            result = calculateTraj(initialVelocity, solverNbr);
            previousThree = previousTwo;
            previousTwo = previousOne;
            previousOne = result;

            if (result.getX() < lastPosTitan.getX()) {
                initialVelocity.setX(initialVelocity.getX() + step);
            } else if (result.getX() > lastPosTitan.getX()) {
                initialVelocity.setX(initialVelocity.getX() - step);
            }
            if (result.getY() < lastPosTitan.getY()) {
                initialVelocity.setY(initialVelocity.getY() + step);
            } else if (result.getY() > lastPosTitan.getY()) {
                initialVelocity.setY(initialVelocity.getY() - step);
            }
            if (result.getZ() < lastPosTitan.getZ()) {
                initialVelocity.setZ(initialVelocity.getZ() + step);
            } else if (result.getZ() > lastPosTitan.getZ()) {
                initialVelocity.setZ(initialVelocity.getZ() - step);
            }
            if(velocity(initialVelocity)){
                if(result.dist(lastPosTitan)<100000){
                    minDist = result.dist(testDist);
                    break;
                }
            }
            if (result.dist(lastPosTitan) < minDist) {
                total++;
                testDist = new Vector3d(getLastPos().getX(),getLastPos().getZ(),getLastPos().getZ());
                minDist = result.dist(testDist);
            }
            if ((result.dist(previousThree) == 0)) {
                float l = 2;
                step = step / l;
                previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
        return initialVelocity;
    }

    /**
     * Calculates the trajectory of the probe, used to improve the results of the bruteforce
     * @param init initial velocity computed by the bruteforce
     * @param solver solver number
     * @return the last position of the probe
     */
    public static Vector3d calculateTraj(Vector3dInterface init,int solver){
        SOLVER = solver;
        ProbeSimulator simulator = new ProbeSimulator();
        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, init, FINALTIME[solver-1], STEPSIZE);
        trajectoryOfAll = simulator.getTrajectory();
        GenerateVectors generateVectors = new GenerateVectors();
        lastPos = generateVectors.generate(trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition(),200000);
        return trajectoryOfProbe[trajectoryOfProbe.length-1];
    }

    /**
     * Get titan's last position
     * @return titan's last position
     */
    public Vector3d getLastPos(){
        return lastPos;
    }

    /**
     * Check the speed of the probe
     * @param vel velocity of the probe
     * @return true, if velocity superior to 60.1km/s
     */
    public static boolean velocity(Vector3dInterface vel){
        double magnitude = vel.norm();
        return magnitude <19900;
    }

    /**
     * Finds the optimal time at which we reach titan, with initial velocity < 20000 (to be able to use the thrust to reach that initial velocity)
     * @param solver solver number
     * @return the final time
     */
    public double timeBruteForce(int solver){
        int timeStep = 50000000;
        while(timeStep>1000){
            //  System.out.println(timeStep);
            Vector3dInterface bruteForceResult = computeBruteforce(solver,1);
            System.out.println(timeStep + " " +FINALTIME[solver-1]+ "   "+bruteForceResult.norm());
            if(velocity(bruteForceResult)){
                VELOCITIES[solver-1] = bruteForceResult;
                return FINALTIME[solver-1];
            }else{
                FINALTIME[solver-1]+= timeStep;
            }
        }
        return FINALTIME[solver-1];
    }

    /**
     * Computes the brute force with more precision, used after we get the final time to make the results more accurate
     * @param solver solver number
     * @return the initial velocity, with a good precision
     */
    public Vector3dInterface precisionBruteforce(int solver){
        Vector3dInterface bruteForceResult = computeBruteforce(solver,1);
        System.out.println(FINALTIME[solver-1]+ "   "+bruteForceResult.norm());
        VELOCITIES[solver-1] = bruteForceResult;
        return VELOCITIES[solver-1];

    }
}