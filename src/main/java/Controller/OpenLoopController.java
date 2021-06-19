package Controller;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.Lander;
import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.CalculationForProbe.OrbitPlanet;

import java.util.ArrayList;

import static Constant.Constant.*;
import static Constant.Constant.SOLVER;

public class OpenLoopController {

    private final double RADIUS_TITAN = 2575.5e3;

    /**
     * Determines the best time for release of the landing module
     * Updates the solarSystem with the correct state of the lander after release
     * @param solarSystem state of the planets and the probe for each step
     * @return updated solarSystem with correct state of the lander
     */
    public ArrayList<Lander> land(State[] solarSystem) {

        int landing = 0;
        double distanceTitanToProbe = solarSystem[0].celestialBody.get(8).getPosition().dist(solarSystem[0].position);

        for (int i = 0; i < solarSystem.length; i++) {
            PlanetBody titan = solarSystem[i].celestialBody.get(8);

            if (solarSystem[i].position.dist(titan.getPosition()) < distanceTitanToProbe) {
                distanceTitanToProbe = solarSystem[i].position.dist(titan.getPosition());
                landing = i;
            }
        }

        // TODO: Find time of landing and use this for position Titan
        solarSystem[landing].updateLander(setLander(solarSystem[landing].getLander(), solarSystem[landing].celestialBody.get(8).getPosition(), (Vector3d) solarSystem[landing].position));


        solarSystem[landing].getLander().setPosition(new Vector2d(-34355, solarSystem[landing].getLander().getPosition().getY()));
        //Phase 1 = rotate our lander to 90 degree (Horizontal state), depends on which X we have minus or plus V_rotation = 0
        ArrayList<Lander> phase1 = new RotationPhase().rotationPhase(solarSystem[landing].getLander(), 20, 0.1, 90.0);

//        for (int i = 0; i < phase1.size(); i++) {
//            Lander t = phase1.get(i);
//            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
//            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
//            System.out.println("Degree = " + t.getRotation());
//            System.out.println("Degree velocity = " + t.getRotationVelocity());
//        }

//        if(true)
//            throw new RuntimeException("stop");

        //todo below each phase we can write System.out.print to check how we cope with this phase

        //Phase 2 = run the main thruster to reach X position = 0 Vx = 0
        //todo implement calculate time depends on distance (IMPORTANT)
        ArrayList<Lander> phase2 = new Phase2().phase2(phase1.get(phase1.size() - 1), 90, 0.1);

//        for (int i = 0; i < phase2.size(); i++) {
//            Lander t = phase2.get(i);
//            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
//            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
//            System.out.println("Degree = " + t.getRotation());
//            System.out.println("Degree velocity = " + t.getRotationVelocity());
//        }

//        if(true)
//            throw new RuntimeException("stop");

        //Phase 3 = rotate our lander to 0 degree (Vertical state) V_rotation = 0
        ArrayList<Lander> phase3 = new RotationPhase().rotationPhase(phase2.get(phase2.size()-1), 20, 0.1, 0.0);

//        for (int i = 0; i < phase3.size(); i++) {
//            Lander t = phase3.get(i);
//            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
//            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
//            System.out.println("Degree = " + t.getRotation());
//            System.out.println("Degree velocity = " + t.getRotationVelocity());
//        }

//        if(true)
//            throw new RuntimeException("stop");

        //Phase 4 = final phase, run the main thruster like to reach Y position = 0 and Vy = 0
        ArrayList<Lander> phase4 = new Phase4().phase4(phase3.get(phase3.size()-1), 0.1);

//        for (int i = 0; i < phase4.size(); i++) {
//            Lander t = phase4.get(i);
//            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
//            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
//            System.out.println("Degree = " + t.getRotation());
//            System.out.println("Degree velocity = " + t.getRotationVelocity());
//        }

        //Write all data into one array
        ArrayList<Lander> result = new ArrayList<>(phase1);

        result.addAll(phase2);

        result.addAll(phase3);

        result.addAll(phase4);

        return result;
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
    public Lander setLander(Lander lander, Vector3d posTitan, Vector3d posProbe) {
        Vector2d position = new Vector2d();
        position.setX(0);

        position.setY(posTitan.dist(posProbe) - RADIUS_TITAN);
        lander.setPosition(position);

        lander.setRotation(0);

        lander.setRotationVelocity(0);

        Vector2d velocity = new Vector2d();
        velocity.setX(0);
        velocity.setY(0);
        lander.setVelocity(velocity);

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
