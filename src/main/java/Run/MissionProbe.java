package Run;

import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.CalculationForProbe.OrbitPlanet;

import static Constant.Constant.*;
import static Constant.Constant.STEPSIZE;

public class MissionProbe {

    /**
     * Calculate Trajectories
     * @return Trajectories of the probe
     */
    public State[] trajectoryProbeCalculationToTitan(){

        ProbeSimulator simulator = new ProbeSimulator();

        simulator.trajectory(STARTPOS, VELOCITIES[SOLVER-1], FINALTIME[SOLVER-1], STEPSIZE);

        return simulator.getTrajectory();
    }

    /**
     * Calculate Trajectories
     * @return Trajectories of the probe
     */
    public State[] trajectoryProbeCalculationOrbitTitan(State currentState){

        double day = 24*60*60;
        double year = 30 * day;

        State cloneState = currentState.clone();


        Vector3d orbitVelocity = new OrbitPlanet().orbitSpeed((Vector3d) cloneState.position, cloneState.celestialBody.get(8).getPosition());

        orbitVelocity = (Vector3d) orbitVelocity.add(cloneState.celestialBody.get(8).getVelocity());

        cloneState.velocity = orbitVelocity;
        cloneState.celestialBody.get(11).setVelocity(orbitVelocity);


        return (State[]) new ODESolver().solve(new ODEFunction(), cloneState, year, 60);
    }

    /**
     * Calculate Trajectories
     * @return Trajectories of the probe
     */
    public State[] trajectoryProbeCalculationToEarth(State currentState){

        State cloneState = currentState.clone();

        Vector3d backToHome = new Vector3d(-19457.51190185663,8694.88542609827800331,1332.7261805534363);

        cloneState.velocity = backToHome;
        cloneState.celestialBody.get(11).setVelocity(backToHome);


        return (State[]) new ODESolver().solve(new ODEFunction(), cloneState, 6.167E7-84, STEPSIZE);
    }

}
