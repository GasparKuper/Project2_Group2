package Run;

import Body.State;
import Body.Vector3d;
import ODESolver.ProbeSimulator;

import static Constant.Constant.*;

/**
 * Outputs the result of solvers
 */
public class CalculationOutput extends Thread{
    private Vector3d[] trajectoryOfProbe;
    private Body.State[] trajectoryOfAll;

    public void Solver() {
        ProbeSimulator simulator = new ProbeSimulator();

        double day = 24*60*60;
        double year = 365.25*day;
        double ten_minutes = 60 * 10;

        //Array the trajectory of the probe
        if(THRUST){
            this.trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, new Vector3d(0, 0, 0), 6.167E7, ten_minutes);
        } else {
            this.trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, VELOCITIES[SOLVER-1], FINALTIME[SOLVER-1], STEPSIZE);
        }


        trajectoryOfAll = simulator.getTrajectory();

        System.out.println("Thrust = " + THRUST);
        System.out.println("Fuel = " + FUEL);

        if(SOLVER == 1)
            System.out.println("SYMPLECTIC EULER SOLVER");
        else if(SOLVER == 2)
            System.out.println("IMPLICIT EULER SOLVER");
        else if(SOLVER == 3)
            System.out.println("VELOCITY-VERLET SOLVER");
        else if(SOLVER == 4)
            System.out.println("4th-RUNGE-KUTTA SOLVER");
        //Probe
        System.out.println("Probe = " + trajectoryOfProbe[trajectoryOfProbe.length-1].toString());
        //Titan
        System.out.println("Titan = " + trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition());
        //Distance between Titan and the probe
        System.out.println("Distance between titan and the probe = " + (trajectoryOfProbe[trajectoryOfProbe.length-1].dist(trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition())-2575.5e3));
    }

    /**
     * Gets the final position of the probe
     * @return vector with the final position of the probe
     */
    public Vector3d getFinalPos(){
        return this.trajectoryOfProbe[trajectoryOfProbe.length-1];
    }

    /**
     * Gets the last position of the Titan
     * @return vector with the last position of the Titan
     */
    public Vector3d getTitanLastPos(){
        return this.trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition();
    }
}
