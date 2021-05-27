package Run;

import Body.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;

import java.util.Scanner;

import static Constant.Constant.*;

public class RunSolver {

    private void Solver(){
        Scanner scan = new Scanner(System.in);

        //Chose the Solver
        System.out.println("IMPLICIT EULER = 1");
        System.out.println("SYMPLECTIC EULER = 2");
        System.out.println("VELOCITY-VERLET = 3");
        System.out.println("4th order RUNGE-KUTTA = 4");
        int solver = 0;
        while (!(solver > 0 && solver < 5)) {
            System.out.print("\nWrite which solver you prefer: ");
            solver = scan.nextInt();
            if(solver <= 0 || solver >= 5)
                System.out.println("Wrong solver, Try again!");
        }
        SOLVER = solver;

        //Thrust
        System.out.println("Do you want to use the thrust for the probe");
        String thrust = "Q";
        while (!(thrust.equals("Y") || thrust.equals("N"))) {
            System.out.println("Y/N");
            thrust = scan.nextLine();
            thrust = thrust.toUpperCase();
        }

        THRUST = thrust.equals("Y");

        Vector3d velocity = new Vector3d(0, 0, 0);
        if(!THRUST) {
            //Initial Velocity
            System.out.println("\nLaunch Position: x= 4301000.0, y= -4692000.0, z= -276000.0");
            System.out.println("\nWrite your initial velocity: ");
            System.out.println("For example:");
            System.out.println("x= 34127.250682276586");
            System.out.println("y= -45331.80455765947");
            System.out.println("z= -1860.62108143532");
            System.out.println("Or you want try 'example' velocity");
            String flag = "Q";
            while (!(flag.equals("Y") || flag.equals("N"))) {
                System.out.println("Y/N");
                flag = scan.nextLine();
                flag = flag.toUpperCase();
            }
            if (flag.equals("N")) {
                System.out.println("Initial velocity");
                System.out.print("\nx=");
                double x = scan.nextDouble();
                System.out.print("\ny=");
                double y = scan.nextDouble();
                System.out.print("\nz=");
                double z = scan.nextDouble();
                velocity = new Vector3d(x, y, z);
            } else {
                velocity = new Vector3d(34127.250682276586, -45331.80455765947, -1860.62108143532);
            }
        } else {
            double fuel = 0;
            while (!(fuel > 0)) {
                //Fuel
                System.out.println("How much fuel do you want to refuel in the rocket?");
                fuel = scan.nextDouble();
                if(fuel <= 0)
                    System.out.println("Try again!");
            }
            FUEL = fuel;
        }

        //Parameters
        System.out.println("Your initial speed is = " + velocity(velocity)/1000.0 + "km/s");

        System.out.println("\nWrite your final time in seconds:");
        System.out.println("For example: 1 year 6 hour = 31557600 seconds");
        double finalTime = scan.nextDouble();

        System.out.println("\nWrite which step size in seconds you want to use: ");
        System.out.println("For example: 1 day = 86400 seconds");
        double stepSize = scan.nextDouble();

        Vector3d pos = new Vector3d(4301000.0, -4692000.0, -276000.0);
        System.out.print("\nWe use ");
        if(SOLVER == 1)
            System.out.print("SYMPLECTIC EULER SOLVER ");
        else if(SOLVER == 2)
            System.out.print("IMPLICIT EULER SOLVER ");
        else if(SOLVER == 3)
            System.out.print("VELOCITY-VERLET SOLVER ");
        else if(SOLVER == 4)
            System.out.print("STORMER-VERLET SOLVER ");
        System.out.println("\nThrust = " + THRUST);
        System.out.println("Fuel tank size = " + FUEL);
        System.out.println("Position of the probe = (" + pos.getX() + " " + pos.getY() + " " + pos.getZ() + ")");
        System.out.println("Initial velocity of the probe = (" + velocity.getX() + " " + velocity.getY() + " " + velocity.getZ() + ")");
        System.out.println("Final time = " + finalTime);
        System.out.println("Step size = " + stepSize);

        ProbeSimulator simulator = new ProbeSimulator();
        System.out.println("\nProgram starts calculating");
        Vector3d[] trajectory = (Vector3d[])simulator.trajectory(pos, velocity, finalTime, stepSize);

        //Output of the trajectory of the probe
        for (int i = 0; i < trajectory.length; i++) {
            String row = "Step = " + i + "," + trajectory[i].getX() + "," + trajectory[i].getY() + "," + trajectory[i].getZ();
            System.out.println(row);
        }

    }

    /**
     * Gets the speed of the velocity
     * @param vel vector to check
     * @return the speed of the vector
     */
    private double velocity(Vector3dInterface vel){
        return vel.norm();
    }


    public static void main(String[] args) {
        RunSolver run = new RunSolver();
        run.Solver();
    }
}
