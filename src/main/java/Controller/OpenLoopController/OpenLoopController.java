package Controller.OpenLoopController;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.Lander;
import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import Run.MissionProbe;

import java.util.ArrayList;

public class OpenLoopController {

    private final double RADIUS_TITAN = 2575.5e3;

    /**
     * Determines the best time for release of the landing module
     * Updates the solarSystem with the correct state of the lander after release
     * @return updated solarSystem with correct state of the lander
     */
    public ArrayList<Lander> land() {

        //Solar System state of the planets and the probe for each step
        State[] solarSystem = probeOnTheOrbitTitan();

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


        solarSystem[landing].getLander().setPosition(new Vector2d(-288000, solarSystem[landing].getLander().getPosition().getY()));

        return calculatePhase(solarSystem[landing].getLander());
    }

    /**
     * Compile all phases into one
     * @param state current state of the lander
     * @return landing trajectory
     */
    private ArrayList<Lander> calculatePhase(Lander state){

        //Phase 1 = rotate our lander to 90 degree (Horizontal state), depends on which X we have minus or plus V_rotation = 0
        double theta = 90.0;
        if(state.getPosition().getX() > 0.0)
            theta = -90.0;

        ArrayList<Lander> phaseRotate90 = new RotationPhaseOpen().rotationPhase(state, 20, 0.1, theta);

//        printResult(phaseRotate90);

        //Phase 2 = run the main thruster to reach X position = 0 Vx = 0
        double distance = (Math.abs(phaseRotate90.get(phaseRotate90.size() - 1).getPosition().getX())/2000.0) + 40;
        ArrayList<Lander> phaseSpeedUpX = new PhaseXSpeedUpOpen().phaseSpeedUp(phaseRotate90.get(phaseRotate90.size() - 1), distance, 0.1);

        double theta_Phase2 = -90.0;
        if(theta == -90.0)
            theta_Phase2 = 90.0;

        ArrayList<Lander> phaseRotateToSlowDown = new RotationPhaseOpen().rotationPhase(phaseSpeedUpX.get(phaseSpeedUpX.size()-1), 20, 0.1, theta_Phase2);

        ArrayList<Lander> phaseToSlowDown = new PhaseXSlowDownOpen().phaseToSlowDownX(phaseRotateToSlowDown.get(phaseRotateToSlowDown.size()-1), 0.1);

//        printResult(phaseToSlowDown);

        //Phase 3 = rotate our lander to 0 degree (Vertical state) V_rotation = 0
        ArrayList<Lander> phaseRotateTo0 = new RotationPhaseOpen().rotationPhase(phaseToSlowDown.get(phaseToSlowDown.size()-1), 20, 0.1, 0.0);

//        printResult(phaseRotateTo0);

        //Phase 4 = final phase, run the main thruster like to reach Y position = 0 and Vy = 0
        ArrayList<Lander> phaseLanding = new PhaseLandingOpen().phaseLanding(phaseRotateTo0.get(phaseRotateTo0.size()-1), 0.1);

//        printResult(phaseLanding);

        System.out.println("FUEL need for this landing = " + phaseLanding.get(phaseLanding.size() - 1).getFuel());
        //Write all data into one array
        ArrayList<Lander> result = new ArrayList<>(phaseRotate90);
        result.addAll(phaseSpeedUpX);
        result.addAll(phaseRotateToSlowDown);
        result.addAll(phaseToSlowDown);
        result.addAll(phaseRotateTo0);
        result.addAll(phaseLanding);

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
    private Lander setLander(Lander lander, Vector3d posTitan, Vector3d posProbe) {
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
    private State[] probeOnTheOrbitTitan(){
        MissionProbe probe = new MissionProbe();

        State[] toTitanTrajectory = probe.trajectoryProbeCalculationToTitan();

        return probe.trajectoryProbeCalculationOrbitTitan(toTitanTrajectory[toTitanTrajectory.length - 1]);
    }

    /**
     * Print the result of the phase
     * @param phase result with the data of the phase
     */
    private void printResult(ArrayList<Lander> phase){
        for (int i = 0; i < phase.size(); i++) {
            Lander t = phase.get(i);
            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
            System.out.println("Degree = " + t.getRotation());
            System.out.println("Degree velocity = " + t.getRotationVelocity());
        }
        if(true)
            throw new RuntimeException("stop");
    }

    public static void main(String[] args) {
        OpenLoopController mission = new OpenLoopController();
        mission.land();
    }
}
