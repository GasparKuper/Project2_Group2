import static org.junit.jupiter.api.Assertions.*;

import Body.Vector.Vector3d;
import Interfaces.ProbeSimulatorInterface;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.System;

import static Constant.Constant.SOLVER;

class TrajectoryTest {

    @Test
    @DisplayName("Test trajectory of Symplectic Euler")
    public void testTrajectorySymplecticEuler(){
        SOLVER = 1;
        Vector3dInterface[] trajectory = testSolverOutput();

        assertTrue(testOnNan(trajectory));
        assertTrue(testOnPositiveInfinity(trajectory));
        assertTrue(testOnNagativeInfinity(trajectory));
    }

    @Test
    @DisplayName("Test trajectory of Implicit Euler")
    public void testTrajectoryImplicitEuler(){
        SOLVER = 2;
        Vector3dInterface[] trajectory = testSolverOutput();

        assertTrue(testOnNan(trajectory));
        assertTrue(testOnPositiveInfinity(trajectory));
        assertTrue(testOnNagativeInfinity(trajectory));
    }

    @Test
    @DisplayName("Test trajectory of Velocity-Verlet")
    public void testTrajectoryVelocityVerlet(){
        SOLVER = 3;
        Vector3dInterface[] trajectory = testSolverOutput();

        assertTrue(testOnNan(trajectory));
        assertTrue(testOnPositiveInfinity(trajectory));
        assertTrue(testOnNagativeInfinity(trajectory));
    }

    @Test
    @DisplayName("Test trajectory of 4th-order Runge-Kutta")
    public void testTrajectoryRungeKutta(){
        SOLVER = 4;
        Vector3dInterface[] trajectory = testSolverOutput();

        assertTrue(testOnNan(trajectory));
        assertTrue(testOnPositiveInfinity(trajectory));
        assertTrue(testOnNagativeInfinity(trajectory));
    }

    private boolean testOnNan(Vector3dInterface[] trajectory){
        for (int i = 0; i < trajectory.length; i++) {
            if(Double.isNaN(trajectory[i].getX()) ||
                    Double.isNaN(trajectory[i].getY()) ||
                        Double.isNaN(trajectory[i].getZ())){
                return false;
            }
        }
        return true;
    }

    private boolean testOnPositiveInfinity(Vector3dInterface[] trajectory){
        for (int i = 0; i < trajectory.length; i++) {
            if(trajectory[i].getX() == Double.POSITIVE_INFINITY ||
                    trajectory[i].getY() == Double.POSITIVE_INFINITY ||
                        trajectory[i].getZ() == Double.POSITIVE_INFINITY){
                return false;
            }
        }
        return true;
    }

    private boolean testOnNagativeInfinity(Vector3dInterface[] trajectory){
        for (int i = 0; i < trajectory.length; i++) {
            if(trajectory[i].getX() == Double.NEGATIVE_INFINITY ||
                    trajectory[i].getY() == Double.NEGATIVE_INFINITY ||
                        trajectory[i].getZ() == Double.NEGATIVE_INFINITY){
                return false;
            }
        }
        return true;
    }

    private Vector3dInterface[] testSolverOutput() {
        Vector3dInterface[] trajectory = simulateOneYear();
        try {
            String solverStr = null;

            if(SOLVER == 1)
                solverStr = "SYMPLECTIC_EULER";
            else if(SOLVER == 2)
                solverStr = "IMPLICIT_EULER";
            else if(SOLVER == 3)
                solverStr = "VELOCITY_VERLET";
            else if(SOLVER == 4)
                    solverStr = "4th_RUNGE_KUTTA";

            FileWriter writer = new FileWriter("trajectory" + solverStr +  ".csv");
            String header = "day,x,y,z";
            writer.write(header + "\n");
            for (int i = 0; i < trajectory.length; i++) {
                String row = i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
                writer.write(row + "\n");
            }
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        return trajectory;
        }

    @Test
    @DisplayName("Trajectory length Test")
    void testTrajectoryLength() {
        Vector3dInterface[] trajectory = simulateOneYear();
        System.out.println("Trajectory length: " + trajectory.length);
        assertEquals(367, trajectory.length);
    }


    /**
     * Simulation one year of the solar system
     * @return trajectory of the probe
     */
    private static Vector3dInterface[] simulateOneYear() {
        Vector3dInterface probe_relative_position = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3dInterface probe_relative_velocity = new Vector3d(35760.650634765625,-48159.48486328125,-604.095458984375); // 12.0 months
        double day = 24*60*60;
        double year = 365.25*day;
        ProbeSimulatorInterface simulator = new ProbeSimulator();
        Vector3dInterface[] trajectory = simulator.trajectory(probe_relative_position, probe_relative_velocity, year, day);

        return trajectory;
    }

}
