package Run;

import Body.PlanetBody;
import Body.Vector3d;
import ODESolver.ProbeSimulator;

public class BruteForce {

    private final int radiusEarth = 6371000;

    private final double radiusTitan = 2575.5e3;

    private Vector3d posBest;
    private Vector3d velBest;
    private double distance = Double.MAX_VALUE;

    public void BruteForceStart(){

        //List of position composition
        int[] composPos = new int[(radiusEarth/23000)*6];
        int tmp_Pos = radiusEarth*3;
        for (int i = 0; i < composPos.length; i++) {
            composPos[i] = tmp_Pos;
            tmp_Pos -= 23000;
        }

        //Set-Up X (Save your time)
        for (int x = 0; x < composPos.length; x++) {
            System.out.println("X = " + x);
            for (int y = 0; y < composPos.length; y++) {
                for (int z = 0; z < composPos.length; z++) {
                    Vector3d position = new Vector3d(composPos[y], composPos[x], composPos[z]);
                    if (radius(position)) {
                        tryShot(position, new Vector3d(0, 0, 0));
                    }
                }
            }
        }

        System.out.println("The Best Shot");
        System.out.println("Position: " + posBest);
        System.out.println("Velocity: " + velBest);
        System.out.println("Distance: " + distance);
    }

    /**
     * Check position on the Earth
     * @param pos position of the probe
     * @return true if its on the surface of Earth
     */
    private boolean radius(Vector3d pos){
        Vector3d initialPosEarth = new Vector3d(-147192316663.54238892,  -28610002992.464771271, 8291942.4644112586975);
        pos = (Vector3d) pos.add(initialPosEarth);
        double distance = pos.dist(initialPosEarth);
        return distance == radiusEarth;
    }


    /**
     * Lucky shot
     * @param pos position of the probe
     * @param vel velocity of the probe
     */
    private void tryShot(Vector3d pos, Vector3d vel){
        ProbeSimulator simulator = new ProbeSimulator();

        double day = 24*60*60;

        //Array the trajectory of the probe
        simulator.trajectory(pos, vel, day, day);

        Body.State[] trajectoryOfAll = simulator.getTrajectory();

        compare(trajectoryOfAll);
    }

    /**
     * Check distance between the probe and the Titan, and update the best shot
     * @param trajectory trajectories of each planets in the solar system
     */
    private void compare(Body.State[] trajectory){

        //Check distance between the probe and the Titan
            Vector3d probe = trajectory[0].celestialBody.get(11).getPosition();
            Vector3d titan = new Vector3d(8.792207314653517E11,-1.2042094620928135E12,-1.421029948775191E10);
            double distance = probe.dist(titan) - radiusTitan;
            //Update the best shot
            if(distance < this.distance){
                this.distance = distance;
                PlanetBody tmp_probe = trajectory[0].celestialBody.get(11);
                PlanetBody tmp_earth = trajectory[0].celestialBody.get(3);
                posBest = (Vector3d) tmp_probe.getPosition().sub(tmp_earth.getPosition());
                velBest = (Vector3d) tmp_probe.getVelocity().sub(tmp_earth.getVelocity());
                System.out.println("===============================================================");
                System.out.println("Position: " + posBest);
                System.out.println("Velocity: " + velBest);
                System.out.println("Distance: " + distance);
            }
    }

    public static void main(String[] args) {
        BruteForce run = new BruteForce();
        run.BruteForceStart();
    }
}
