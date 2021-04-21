package MAIN;

import MAIN.Body.Vector3d;
import MAIN.ODESolver.ProbeSimulator;

public class CalculationOutput extends Thread{

    public static void main(String[] args) {
        ProbeSimulator simulator = new ProbeSimulator();

        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(
                new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
                new Vector3d(27978.003182957942, -62341.39349461967 ,-651.590970913659), 31556952, 360);


        MAIN.Body.State[] trajectoryOfAll = simulator.getTrajectory();


        System.out.println(trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(3).position);
    }
}
