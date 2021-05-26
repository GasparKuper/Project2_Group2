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

    private Vector3d [] trajectoryOfProbe;

    private Vector3d titanLastPos, EarthLastPos;

    public void calculateTitanPos(double stepSize, double finalTime , int planet){
        ProbeSimulator simulator = new ProbeSimulator();

        THRUST = false;
        //Array the trajectory of the probe
        this.trajectoryOfProbe= (Vector3d[]) simulator.trajectory(STARTPOS, new Vector3d(19874.77595339754,-29886.750406091705,-941.4823395838957), finalTime, stepSize);


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

        this.titanLastPos = trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(planet).getPosition();
        System.out.println("Titan = " + titanLastPos);
        THRUST = true;
    }

    public double findGas(Vector3d direction, Vector3d currentVelocity, double velocity, double fuel){
        double temp = direction.mul(velocity).sub(currentVelocity).mul(1/EXHAUSTSPEED).norm();
        System.out.println("Gas need:" + (fuel+15000)/Math.exp(temp));
        return (fuel+15000)/Math.exp(temp);
    }



    public void findParameters(double maxtime, double currentTime, State probe){ //figure out how to get current velocity
        //first find impulse needed to get to titan
        double day = 24*60*60 ;
        double year =  365.25*day;
            Vector3d temp = (Vector3d) titanLastPos.sub(probe.position);
            this.direction = temp.Normalize();
            double averageVelocity = titanLastPos.dist(probe.position)/(year-currentTime);
            this.consume = findGas(this.direction, (Vector3d) probe.velocity, averageVelocity, probe.fuel);
    }
}
