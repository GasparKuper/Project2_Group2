package Run;

import Body.State;
import Body.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;

import static Constant.Constant.*;

public class InitialValuesCalculation {
    private static double minDist = Double.MAX_VALUE;
    private static Vector3d lastPos;
    private Vector3dInterface initialVelocity;
    private Vector3d testDist;
    private static Vector3dInterface bruteForceResult;
    private static State[] trajectoryOfAll;

    public static void main(String[] args) {
        InitialValuesCalculation init = new InitialValuesCalculation();
        boolean end = false;
        // init.computeBruteforce(3);
        for (int i = 0; i < 4; i++) {
        System.out.println("We reach titan at " +init.timeBruteForce(i+1));
        System.out.println("With initial speed of " +VELOCITIES[i].norm());
        System.out.println("And initial velocity is "+VELOCITIES[i].toString());
        System.out.println("Distance to titan is : "+calculateTraj(VELOCITIES[i],i+1).dist(lastPos));
        System.out.println("Last pos probe : "+calculateTraj(VELOCITIES[i],i+1));
        System.out.println("Last pos titan "+lastPos);
        }
    }
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
            // System.out.println("Current velocity " + initialVelocity.toString() + " step " + step);
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
                    //     System.out.println("END");
                    //     System.out.println("norm is "+initialVelocity.norm());
                    //     System.out.println("new dist is " + minDist);
                    //    System.out.println("probe final position " + result);
                    //  System.out.println("Best velocity " + initialVelocity.toString());
                    break;

                }
            }
            //2,675,500
            //2,875,500
            if (result.dist(lastPosTitan) < minDist) {
                total++;
                testDist = new Vector3d(getLastPos().getX(),getLastPos().getZ(),getLastPos().getZ());
                //  testDist=trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition();
                //   System.out.println("norm is "+initialVelocity.norm());
                minDist = result.dist(testDist);
                //     System.out.println("new dist is " + minDist);
                //    System.out.println("probe final position " + result);
                //   System.out.println("Best velocity " + initialVelocity.toString());
            }
            if ((result.dist(previousThree) == 0)) {
                float l = 2;
                step = step / l;
                previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
 /*       System.out.println("Initial time is " + FINALTIME[solverNbr-1]);
        System.out.println("Initial velocity in km/s "+ initialVelocity.norm()/1000);
        System.out.println("Final dist is " + minDist);
        System.out.println("Initial velocity " + initialVelocity);
        System.out.println("probe final position " + calculateTraj(initialVelocity, solverNbr));
        System.out.println("titan final position " + lastPosTitan);*/
        return initialVelocity;
    }
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
    public static boolean velocity(Vector3dInterface vel){
        double magnitude = vel.norm();
        return magnitude <19900;
    }
    public double timeBruteForce(int solver){
        //     int timeStep = 500000;
        //  VELOCITIES[solver-1] = computeBruteforce(solver,1E-15);
        // return FINALTIME[solver-1];
        //   int i =0;
        int timeStep = 10000000;
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
}