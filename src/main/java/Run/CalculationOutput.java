package Run;

import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;

import java.sql.SQLOutput;

import static Constant.Constant.*;

/**
 * Outputs the result of solvers
 */
public class  CalculationOutput{
    private Vector3d[] trajectoryOfProbe;
    private State[] trajectoryOfAll;

    public void Solver() {

        if(SOLVER == 1)
            System.out.println("SYMPLECTIC EULER SOLVER");
        else if(SOLVER == 2)
            System.out.println("IMPLICIT EULER SOLVER");
        else if(SOLVER == 3)
            System.out.println("VELOCITY-VERLET SOLVER");
        else if(SOLVER == 4)
            System.out.println("4th-RUNGE-KUTTA SOLVER");

        MissionProbe probe = new MissionProbe();

        State[] toTitan = probe.trajectoryProbeCalculationToTitan();

        System.out.println("Start launch position:");
        System.out.println(toTitan[0].position);
        System.out.println("Position of Earth");
        System.out.println(toTitan[0].celestialBody.get(3).getPosition());
        System.out.println("Distance between center of the Earth and the Probe");
        System.out.println(toTitan[0].position.dist(toTitan[0].celestialBody.get(3).getPosition()) + " meters");

        System.out.println("\nReach the Titan after 713 day 18 hours 33 minutes 20 seconds");
        System.out.println("Position of the probe");
        System.out.println(toTitan[toTitan.length - 1].position);
        System.out.println("Position of Titan");
        System.out.println(toTitan[toTitan.length - 1].celestialBody.get(8).getPosition());
        System.out.println("Distance between Titan and Probe");
        System.out.println((toTitan[toTitan.length - 1].position.dist(toTitan[toTitan.length - 1].celestialBody.get(8).getPosition())-2575.5e3) + " meters");

        State[] orbitTitan = probe.trajectoryProbeCalculationOrbitTitan(toTitan[toTitan.length - 1]);

        System.out.println("\nStay on the orbit of Titan 30 days");
        System.out.println("The lase position of the probe before leaving the orbit");
        System.out.println(orbitTitan[orbitTitan.length - 1].position);
        System.out.println("Position of Titan");
        System.out.println(orbitTitan[orbitTitan.length - 1].celestialBody.get(8).getPosition());
        System.out.println("Distance between Titan and Probe");
        System.out.println((orbitTitan[orbitTitan.length - 1].position.dist(orbitTitan[orbitTitan.length - 1].celestialBody.get(8).getPosition())-2575.5e3) + " meters");

        State[] toEarth = probe.trajectoryProbeCalculationToEarth(orbitTitan[orbitTitan.length - 1]);

        System.out.println("\nReach the Earth after 713 day 18 hours 31 minutes 56 seconds");
        System.out.println("Position of the probe");
        System.out.println(toEarth[toEarth.length - 1].position);
        System.out.println("Position of Earth");
        System.out.println(toEarth[toEarth.length - 1].celestialBody.get(3).getPosition());
        System.out.println("Distance between Titan and Probe");
        System.out.println((toEarth[toEarth.length - 1].position.dist(toEarth[toEarth.length - 1].celestialBody.get(8).getPosition())-6371000) + " meters");

    }
}
