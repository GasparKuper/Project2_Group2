package Controller;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.Lander;
import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.CalculationForProbe.OpenLoopBruteForce;
import Run.CalculationForProbe.OrbitPlanet;

import static Constant.Constant.*;
import static Constant.Constant.SOLVER;

public class OpenLoopController {

    private final double DISTANCE_TO_CENTER = 2.0;
    public final double G = 1.352;
    private final double RADIUS_TITAN = 2575.5e3;
    private double t = 0;
    private double FORCE;
    private static Vector2d INIT_POS;
    private static Vector2d INIT_VEL;
    private static double INIT_ANG_POS;
    private static double INIT_ANG_VEL;

    /**
     * Determines the best time for release of the landing module
     * Updates the solarSystem with the correct state of the lander after release
     * @param solarSystem state of the planets and the probe for each step
     * @return updated solarSystem with correct state of the lander
     */
    public State[] land(State[] solarSystem) {

        int landing = 0;
        double distanceTitanToProbe = solarSystem[0].celestialBody.get(11).getPosition().dist(solarSystem[0].position);

        for (int i = 0; i < solarSystem.length; i++) {
            PlanetBody titan = solarSystem[i].celestialBody.get(11);

            if (solarSystem[i].position.dist(titan.getPosition()) < distanceTitanToProbe) {
                distanceTitanToProbe = solarSystem[i].position.dist(titan.getPosition());
                landing = i;
            }
        }

        // TODO: Find time of landing and use this for position Titan
        solarSystem[landing].updateLander(setLander(solarSystem[landing], solarSystem[landing].getLander(), solarSystem[landing].celestialBody.get(11).getPosition(), (Vector3d) solarSystem[landing].position));

        OpenLoopBruteForce calculator = new OpenLoopBruteForce(solarSystem[landing].getLander());
        FORCE = calculator.findThrust();

        // TODO: Set solarSystem.length to time of landing
        for (int i = landing + 1; i < solarSystem.length; i++) {
            solarSystem[i].updateLander(step(t, FORCE, solarSystem[i-1].getLander()));
            t = t + STEPSIZE;
        }

        return solarSystem;
    }

    /**
     * Updates the lander for one step
     * @param t time after release of the landing module
     * @return movement of the probe at step t
     **/
    // TODO: implement fuel use
    public Lander step(double t, double force, Lander lander) {

        Lander newLander = new Lander(lander.getPosition(), lander.getVelocity(), lander.getMass(), lander.getFuel(), lander.getRotation(), lander.getRotationVelocity());

        Vector2d position = new Vector2d();
        //position.setX(- force * Math.sin(lander.getRotation() + INIT_VEL.getX() * t + INIT_POS.getX()));
        //position.setY(- force * Math.cos(lander.getRotation()) - Math.sqrt(t) * G / 2 + INIT_VEL.getY() * t + INIT_POS.getY());
        position.setX(lander.getPosition().getX() + lander.getVelocity().getX() / STEPSIZE);
        position.setY(lander.getPosition().getY() + lander.getVelocity().getY() / STEPSIZE);
        newLander.setPosition(position);

        Vector2d velocity = new Vector2d();
        //velocity.setX(- force * Math.cos(lander.getRotation()) + INIT_VEL.getX());
        //velocity.setY(- force * Math.sin(lander.getRotation()) - G * t + INIT_VEL.getY());
        velocity.setX(lander.getVelocity().getX() + force * Math.sin(lander.getRotation()));
        velocity.setY(lander.getVelocity().getY() + force * Math.cos(lander.getRotation()) - G);

        //Wind
//        velocity = velocity.add(lander.generateRandomWind());

        newLander.setVelocity(velocity);

        newLander.setRotation(Math.sqrt(t) * force / 2 + INIT_ANG_VEL * t + INIT_ANG_POS );

        newLander.setRotationVelocity(force * t + INIT_ANG_VEL);

        return newLander;
    }

    /**
     * Sets the state of a lander at time of release
     * @param lander lander of which to set position, velocity, rotation and rotationVelocity
     * @param posTitan position of Titan at time of landing
     * @param posProbe position of of the probe at time of release lander
     * @return lander with correct state
     **/
    // TODO: Set fuel
    // TODO: Change posTitan to its position at time of landing
    public Lander setLander(State solarSystem, Lander lander, Vector3d posTitan, Vector3d posProbe) {
        Vector2d position = new Vector2d();
        position.setX(0);
        position.setY(posTitan.dist(posProbe) - RADIUS_TITAN);
        lander.setPosition(position);
        INIT_POS = position;

        lander.setRotation(0);
        INIT_ANG_POS = 0;

        lander.setRotationVelocity(0);
        INIT_ANG_VEL = 0;

        Vector2d velocity = new Vector2d();
        velocity.setX(solarSystem.velocity.getY());
        velocity.setY(solarSystem.velocity.getX());
        lander.setVelocity(velocity);
        INIT_VEL = velocity;

        return lander;
    }

    /**
     * Simulation of the solar system
     * @return The State with the minimal distance between Titan and Probe
     */
    public State[] probeOnTheOrbitTitan(){
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

        return orbitTitan;
    }

    public static void main(String[] args) {
        OpenLoopController mission = new OpenLoopController();
        mission.land(mission.probeOnTheOrbitTitan());
    }
}
