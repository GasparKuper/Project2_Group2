package Run;

import Body.Vector3d;
import ODESolver.ProbeSimulator;


import static Constant.Constant.SOLVER;

public class InitialValuesCalculation {
    private final int radiusEarth = 6371000;

    private final double radiusTitan = 2575.5e3;

    private double minDist = Double.MAX_VALUE;
    public void run(){
        for(int i =0;i<4;i++) {
            int solver = i+1;
            Vector3d initialPos = new Vector3d(-5796000.0,-2645000,0);
            int total = 0;
            float step = 10000;
            Vector3d previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            Vector3d previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            Vector3d previousThree;

            Vector3d initialVelocity = new Vector3d(0, 0, 0);

            while (step > 1E-16) {
                //   System.out.println("Current velocity " + initialVelocity.toString() + " step " + step);
                if(velocity(initialVelocity))
                    throw new RuntimeException("VELOCITY IS TOO BIG");
                //index 0 = Probe  index 1 = Titan
                Vector3d[] result = tryShot(initialPos, initialVelocity, solver);

                previousThree = previousTwo;
                previousTwo = previousOne;
                previousOne = result[0];

                if (result[0].getX() < result[1].getX()) {
                    initialVelocity.setX(initialVelocity.getX() + step);
                } else if (result[0].getX() > result[1].getX()) {
                    initialVelocity.setX(initialVelocity.getX() - step);
                }
                if (result[0].getY() < result[1].getY()) {
                    initialVelocity.setY(initialVelocity.getY() + step);
                } else if (result[0].getY() > result[1].getY()) {
                    initialVelocity.setY(initialVelocity.getY() - step);
                }
                if (result[0].getZ() < result[1].getZ()) {
                    initialVelocity.setZ(initialVelocity.getZ() + step);
                } else if (result[0].getZ() > result[1].getZ()) {
                    initialVelocity.setZ(initialVelocity.getZ() - step);
                }
//                if (result[0].dist(lastPosTitan) < minDist) {
//                    total++;
//                    minDist = result.dist(lastPosTitan);
//                    //   System.out.println("new dist is " + minDist);
//                    //  System.out.println("probe final position " + result);
//                    //   System.out.println("Best velocity " + initialVelocity.toString());
//                }

//                if ((result.dist(previousThree) == 0)) {
//                    if ((result.dist(previousThree) == 0)) {
//                        // System.out.println("REPEATING, SO WE REDUCE THE STEP SIZE BY HALF !!!!");
//                    }
//                    float l = 2;
//                    step = step / l;
//                    previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
//                    previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
//
//                }

            }
        }
    }
    /**
     * Check the speed of the probe
     * @param vel velocity of the probe
     * @return true, if velocity between 50km/s and 60.1km/s
     */
    private boolean velocity(Vector3d vel){
        double magnitude = vel.norm();
        return (magnitude >= 50000.0 && magnitude <= 60001.0);
    }

    /**
     * Lucky shot
     * @param pos position of the probe
     * @param vel velocity of the probe
     */
    private Vector3d[] tryShot(Vector3d pos, Vector3d vel, int solver){
        SOLVER = solver;
        ProbeSimulator simulator = new ProbeSimulator();

        double day = 24*60*60;
        double year = 365.25*day;

        //Array the trajectory of the probe
        simulator.trajectory(pos, vel, year, day);

        Body.State[] trajectoryOfAll = simulator.getTrajectory();

        return compare(trajectoryOfAll);
    }

    /**
     * Check distance between the probe and the Titan, and update the best shot
     * @param trajectory trajectories of each planets in the solar system
     */
    private Vector3d[]  compare(Body.State[] trajectory){

        Vector3d[] tmp = new Vector3d[2];
        //Check distance between the probe and the Titan
        for (int i = 0; i < trajectory.length; i++) {
            Vector3d probe = trajectory[i].celestialBody.get(11).getPosition();
            Vector3d titan = trajectory[i].celestialBody.get(8).getPosition();
            double distance = probe.dist(titan) - radiusTitan;
            //Update the best shot
            if(distance < this.minDist){
                minDist = distance;
                tmp[0] = trajectory[i].celestialBody.get(11).getPosition();
                tmp[1] = trajectory[i].celestialBody.get(8).getPosition();
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        InitialValuesCalculation run = new InitialValuesCalculation();

//        run.BruteForceStart();
        run.run();
    }

    public  double getDist(){
        return minDist;
    }
}
