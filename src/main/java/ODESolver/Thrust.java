package ODESolver;

import Body.State;
import Body.Vector3d;

import static Constant.Constant.*;
import static Constant.Constant.SOLVER;

public class Thrust {
    private double consume;
    private Vector3d direction;

    public double getConsume() {
        return consume;
    }

    public Vector3d getDirection() {
        return direction;
    }

    private Vector3d titanLastPos;

    public void calculateTitanPos(double stepSize, double finalTime){
        ProbeSimulator simulator = new ProbeSimulator();

        THRUST = false;
        //Array the trajectory of the probe
        simulator.trajectory(STARTPOS, new Vector3d(0, 0, 0), finalTime, stepSize);



        State[] trajectoryOfAll = simulator.getTrajectory();

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

        this.titanLastPos = trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(8).getPosition();
        //Titan
        System.out.println("Titan = " + titanLastPos);

        THRUST = true;
    }

    public void findDirection(){
        consume = 6;
        direction = new Vector3d( 10, 10, 10);
    }
}
