package Controller.CloseLoopController;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.Lander;
import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import Run.MissionProbe;

import java.util.ArrayList;

public class CloseLoopController {

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


        solarSystem[landing].getLander().setPosition(new Vector2d(-278000, solarSystem[landing].getLander().getPosition().getY()));

        return calculatePhase(solarSystem[landing].getLander(), 3);
    }

    /**
     * Compile all phases into one
     * @param state current state of the lander
     * @return landing trajectory
     */
    public ArrayList<Lander> calculatePhase(Lander state, double rotationTime){

        ArrayList<Lander> result = new ArrayList<>();
        result.add(state);

        if(Math.abs(state.getPosition().getX()) > 0.0) {
            result.addAll(correctX(state, rotationTime));
        }

        //Phase 4 = final phase, run the main thruster like to reach Y position = 0 and Vy = 0
        ArrayList<Lander> phaseLanding = new PhaseLandingClose().phaseLanding(result.get(result.size()-1), 0.1);

//        printResult(phaseLanding, true);

        //Write all data into one array
        result.addAll(phaseLanding);

        return result;
    }

    public ArrayList<Lander> correctX(Lander state, double rotationTime){

        ArrayList<Lander> result = new ArrayList<>();
        //Phase 1 = rotate our lander to 90 degree (Horizontal state), depends on which X we have minus or plus V_rotation = 0
        double theta = 90.0;
        if (state.getPosition().getX() > 0.0)
            theta = -90.0;

        ArrayList<Lander> phaseRotate90 = new RotationPhaseClose().rotationPhase(state, rotationTime, 0.1, theta);

//        printResult(phaseRotate90, true);

        //Phase 2 = run the main thruster to reach X position = 0 Vx = 0
        double time = Math.sqrt(Math.abs(phaseRotate90.get(phaseRotate90.size() - 1).getPosition().getX())/10.0);

        ArrayList<Lander> phaseSpeedUpX = new PhaseXSpeedUpClose().phaseSpeedUp(phaseRotate90.get(phaseRotate90.size() - 1), time, 0.1);

//        printResult(phaseSpeedUpX, true);

        double theta_Phase2 = -90.0;
        if (theta == -90.0)
            theta_Phase2 = 90.0;

        ArrayList<Lander> phaseRotateToSlowDown = new RotationPhaseClose().rotationPhase(phaseSpeedUpX.get(phaseSpeedUpX.size() - 1), rotationTime, 0.1, theta_Phase2);

//            printResult(phaseRotateToSlowDown, true);

        ArrayList<Lander> phaseToSlowDown = new PhaseXSlowDownClose().phaseToSlowDownX(phaseRotateToSlowDown.get(phaseRotateToSlowDown.size() - 1), 0.1);

//        printResult(phaseToSlowDown, true);

        //Phase 3 = rotate our lander to 0 degree (Vertical state) V_rotation = 0
        ArrayList<Lander> phaseRotateTo0 = new RotationPhaseClose().rotationPhase(phaseToSlowDown.get(phaseToSlowDown.size() - 1), rotationTime, 0.1, 0.0);

//        printResult(phaseRotateTo0, true);
        result.addAll(phaseRotate90);
        result.addAll(phaseSpeedUpX);
        result.addAll(phaseRotateToSlowDown);
        result.addAll(phaseToSlowDown);
        result.addAll(phaseRotateTo0);

        return result;
    }

    /**
     * Sets the state of a lander at time of release
     * @param lander lander of which to set position, velocity, rotation and rotationVelocity
     * @param posTitan position of Titan at time of landing
     * @param posProbe position of of the probe at time of release lander
     * @return lander with correct state
     **/
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
    private void printResult(ArrayList<Lander> phase, boolean flag){
        for (int i = 0; i < phase.size(); i++) {
            System.out.println(i);
            Lander t = phase.get(i);
            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
            System.out.println("Degree = " + t.getRotation());
            System.out.println("Degree velocity = " + t.getRotationVelocity());
        }
        if(flag)
            throw new RuntimeException("stop");
    }

    public Lander getLast(){
        Lander lander = new Lander(new Vector2d(-278000, 209000), new Vector2d(0, 0), 6000, 0, 0, 0);
        ArrayList<Lander> trajectory = calculatePhase(lander, 3.0);
        return trajectory.get(trajectory.size()-1);
    }

    public ArrayList<Lander> getSimulation(){
        Lander lander = new Lander(new Vector2d(-278000, 200000), new Vector2d(0, 0), 6000, 0, 0, 0);
        return calculatePhase(lander, 3.0);
    }

    public static void main(String[] args) {
        CloseLoopController mission = new CloseLoopController();
        mission.land();
    }
}
