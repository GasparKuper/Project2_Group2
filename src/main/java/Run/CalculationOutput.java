package Run;

import Body.State;
import Body.Vector3d;
import ODESolver.ProbeSimulator;

import static Constant.Constant.*;

public class CalculationOutput extends Thread{
    private Vector3d[] trajectoryOfProbe;
    private Body.State[] trajectoryOfAll;
    public void Solver() {
        ProbeSimulator simulator = new ProbeSimulator();

        double day = 24*60*60;
        double year = 365.25*day;
        double five_minutes = 60 * 5;

        //Array the trajectory of the probe
        if(THRUST){
            this.trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, new Vector3d(0, 0, 0), year, five_minutes);
        } else {
            this.trajectoryOfProbe = (Vector3d[]) simulator.trajectory(STARTPOS, VELOCITIES[SOLVER - 1], FINALTIME[SOLVER - 1], STEPSIZE);
        }


        trajectoryOfAll = simulator.getTrajectory();

        if(SOLVER == 1)
            System.out.println("SYMPLECTIC EULER SOLVER");
        else if(SOLVER == 2)
            System.out.println("IMPLICIT EULER SOLVER");
        else if(SOLVER == 3)
            System.out.println("VELOCITY-VERLET SOLVER");
        else if(SOLVER == 4)
            System.out.println("STORMER-VERLET SOLVER");
        //Probe
        System.out.println("Probe = " + trajectoryOfProbe[trajectoryOfProbe.length-1].toString());
        //Titan
        System.out.println("Titan = " + trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition());
        //Distance between Titan and the probe
        System.out.println("Distance between titan and the probe = " + trajectoryOfProbe[trajectoryOfProbe.length-1].dist(trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition()));
    }
    public Vector3d getFinalPos(){
        return this.trajectoryOfProbe[trajectoryOfProbe.length-1];
    }
    public Vector3d getTitanLastPos(){
        return this.trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition();
    }
}
