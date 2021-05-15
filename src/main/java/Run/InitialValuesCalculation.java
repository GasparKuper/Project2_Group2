package Run;

import Body.State;
import Body.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;

import java.util.Arrays;

import static Constant.Constant.*;

public class InitialValuesCalculation {
    private static double minDist = Double.MAX_VALUE;
    private static Vector3d lastPos;
    private Vector3dInterface initialVelocity;

    public static void main(String[] args) {
        STEPSIZE = 20000;
        InitialValuesCalculation init = new InitialValuesCalculation();
        boolean end = false;
        for (int i = 0; i < 3; i++) {
            System.out.println("We reach titan at " +init.timeBruteForce(i+1));
            System.out.println("With initial speed of " +VELOCITIES[i].norm());
            System.out.println("And initial velocity is "+VELOCITIES[i].toString());
        }
    }
    public Vector3dInterface computeBruteforce(int solverNbr){
        int solver = solverNbr;
      //  arrivalTime(solver);
        int total = 0;
        float step = 10000;
        Vector3d previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

        initialVelocity = new Vector3d(0, 0, 0);
        Vector3d result = calculateTraj(initialVelocity, solver);
        Vector3d lastPosTitan = getLastPos();
        minDist = result.dist(lastPosTitan);
        while (step > 1E-16) {
            //   System.out.println("Current velocity " + initialVelocity.toString() + " step " + step);
            result = calculateTraj(initialVelocity, solver);
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
            if (result.dist(lastPosTitan) < minDist) {
                total++;
                minDist = result.dist(lastPosTitan);
                //   System.out.println("new dist is " + minDist);
                //  System.out.println("probe final position " + result);
                //   System.out.println("Best velocity " + initialVelocity.toString());
            }
            if ((result.dist(previousThree) == 0)) {
                if ((result.dist(previousThree) == 0)) {
                    // System.out.println("REPEATING, SO WE REDUCE THE STEP SIZE BY HALF !!!!");
                }
                float l = 2;
                step = step / l;
                previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
   /*     System.out.println("Increased " + total);
        System.out.println("Initial velocity in km/s "+ initialVelocity.norm()/1000);
        System.out.println("Final dist is " + minDist);
        System.out.println("Initial velocity " + initialVelocity);
        System.out.println("probe final position " + calculateTraj(initialVelocity, solver));
        System.out.println("titan final position " + lastPosTitan);*/
        return initialVelocity;
    }
    public static Vector3d calculateTraj(Vector3dInterface init,int solver){
        ProbeSimulator simulator = new ProbeSimulator();
        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, init, FINALTIME[solver-1], STEPSIZE);
        State[] trajectoryOfAll = simulator.getTrajectory();
        lastPos = trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).position;
        SOLVER = solver;
        return trajectoryOfProbe[trajectoryOfProbe.length-1];
    }
    public Vector3d getLastPos(){
        return lastPos;
    }
    public Vector3dInterface getInitialVelocity(){
        return initialVelocity;
    }
    /**
     * Check the speed of the probe
     * @param vel velocity of the probe
     * @return true, if velocity superior to 60.1km/s
     */
    private static boolean velocity(Vector3dInterface vel){
        double magnitude = vel.norm();
        return magnitude > 60000.0;
    }
    public double timeBruteForce(int solver){
        FINALTIME[solver-1] = 31556952;
        boolean end = false;
        int timeStep = 10000000;
        while(timeStep>5){
            Vector3dInterface bruteForceResult = computeBruteforce(solver);
            if(velocity(bruteForceResult)){
                if(timeStep/2<5){
                    return FINALTIME[solver-1];
                }
                FINALTIME[solver-1]+=timeStep;
                timeStep= timeStep/2;
            }else{
                VELOCITIES[solver-1]=bruteForceResult;
                FINALTIME[solver-1]-= timeStep;
            }
        }
        return FINALTIME[solver-1];
    }
}