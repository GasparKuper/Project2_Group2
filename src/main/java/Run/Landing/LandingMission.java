package Run.Landing;

import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.CalculationForProbe.OrbitPlanet;
import WindModel.ID;
import WindModel.LandingModule;
import WindModel.WindModel;

import static Constant.Constant.*;
import static Constant.Constant.STEPSIZE;

public class LandingMission {

    private final double radiusTitan = 2575.5e3;

    /**
     * Simulation of the solar system
     * @return The State with the minimal distance between Titan and Probe
     */
    private State findMinDistanceWithTitan(){
        ProbeSimulator simulator = new ProbeSimulator();

        simulator.trajectory(STARTPOS, VELOCITIES[SOLVER-1], FINALTIME[SOLVER-1], STEPSIZE);

        State[] toTitanTrajectory = simulator.getTrajectory();

        double day = 24*60*60;
        double year = 30 * day;

        State cloneState = toTitanTrajectory[toTitanTrajectory.length - 1].clone();

        Vector3d orbitVelocity = new OrbitPlanet().orbitSpeed((Vector3d) cloneState.position, cloneState.celestialBody.get(8).getPosition());

        orbitVelocity = (Vector3d) orbitVelocity.add(cloneState.celestialBody.get(8).getVelocity());

        cloneState.velocity = orbitVelocity;
        cloneState.celestialBody.get(11).setVelocity(orbitVelocity);

        State[] orbitTitan = (State[]) new ODESolver().solve(new ODEFunction(), cloneState, year, 60);

        double minDist = Double.MAX_VALUE;
        int point = 0;
        for (int i = 0; i < orbitTitan.length; i++) {
            Vector3d posProbe = orbitTitan[i].celestialBody.get(11).getPosition();
            Vector3d posTitan = orbitTitan[i].celestialBody.get(8).getPosition();
            double tmp_distance = posProbe.dist(posTitan);
            if(tmp_distance < minDist){
                minDist = tmp_distance;
                point = i;
            }
        }

        return orbitTitan[point];
    }

    /**
     * Start landing mission
     */
    public void startMission(){
        State launchPositionLander = findMinDistanceWithTitan();

        //Calculate the distance between Titan and Probe, this is our X coordinate for the lander
        Vector3d posTitan = launchPositionLander.celestialBody.get(8).getPosition();
        Vector3d posProbe = launchPositionLander.celestialBody.get(11).getPosition();
        double distanceBetweenTitanAndLander = posTitan.dist(posProbe) - radiusTitan;

        Vector2d positionLander = new Vector2d(0, distanceBetweenTitanAndLander);

        //TODO Open-loop controller???
        //todo Here u can get initial velocity of the lander and then calculate, how much fuel u need for the open-loop controller
        Vector3d velocityProbe = (Vector3d) launchPositionLander.velocity;

        //todo real initial velocity of the lander from the launch, can be changed by open-loop controller
//        Vector2d velocityLander = new Vector2d(velocityProbe.getX(), velocityProbe.getY()); //Comment if u need

        //todo uncomment this line, to check animation
        Vector2d velocityLander = new Vector2d(0, 0);

        LandingModule lander = new LandingModule(positionLander, velocityLander, 0, 0, 0, ID.LandingModule);

        new WindModel(lander);
    }


    public static void main(String[] args) {
        new LandingMission().startMission();
    }
}
