package MAIN;

import MAIN.Body.*;
import MAIN.Interfaces.Vector3dInterface;
import MAIN.ODESolver.ProbeSimulator;

import static MAIN.Constant.Constant.SOLVER;
import static MAIN.Constant.Constant.TITANLASTPOS;

public class InitialValuesCalculation {
    private static double minDist = Double.MAX_VALUE;
    public static void main(String[] args) {
        for(int i =0;i<4;i++) {
            int solver = i+1;
            Vector3d lastPosTitan = TITANLASTPOS[i];
            Vector3d startPos = new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10, 8174296.311571818);
            Vector3dInterface initialVelocity;
            int total = 0;
            float step = 10000;
            Vector3d previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            Vector3d previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            Vector3d previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            initialVelocity = new Vector3d(0, 0, 0);
            Vector3d result = calculateTraj(initialVelocity, solver);
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
            System.out.println("Increased " + total);
            System.out.println("Final dist is " + minDist);
            System.out.println("Initial velocity " + initialVelocity);
            System.out.println("probe final position " + calculateTraj(initialVelocity, solver));
            System.out.println("titan final position " + lastPosTitan);
        }
    }
    public static Vector3d calculateTraj(Vector3dInterface init,int solver){
        ProbeSimulator simulator = new ProbeSimulator();

        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(
                new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
                init, 31556952, 360);

        SOLVER = solver;

        return trajectoryOfProbe[trajectoryOfProbe.length-1];
    }
    public static double getDist(){
        return minDist;
    }
}
