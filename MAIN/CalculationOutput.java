package MAIN;

import MAIN.Body.Vector3d;
import MAIN.ODESolver.ProbeSimulator;

import static MAIN.Constant.Constant.SOLVER;

public class CalculationOutput extends Thread{

    public void Solver() {
        ProbeSimulator simulator = new ProbeSimulator();

        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(
                new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
                new Vector3d(27978.003182957942, -62341.39349461967 ,-651.590970913659), 31556952, 360);


        MAIN.Body.State[] trajectoryOfAll = simulator.getTrajectory();

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
        System.out.println("Titan = " + trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).position);
    }
}
