package Titan.Simulator;

import Titan.Body.Vector3d;
import Titan.Interfaces.Vector3dInterface;

public class Mathmodel {

   
     private static double stepsize = 863;
    private static double maxtime = 3.154e+7;
    private static double radiusEarth = 6378000;
    private static double radiusTitan = 2574700;
    private static Vector3dInterface earthposition = new Vector3d(-1.471922101663588e+11,  -2.860995816266412e+10, 8.278183193596080e+06);
    public Vector3dInterface [] trajectoryTitan = new Vector3dInterface[(int) (maxtime/stepsize)+1]; //figure out how to get every position of titan
    public Vector3dInterface [] trajectorySolution = new Vector3dInterface[(int) (maxtime/stepsize)+1];
    public double stepsToSolution;


    public void bruteforce(){
        Boolean solution = false;
        Vector3dInterface[] trajectory = new Vector3dInterface[(int) (maxtime/stepsize)+1];

        while(solution != true){
            double angleX = Math.random() * 2*Math.PI;
            double angleY = Math.random() * Math.PI;

            Vector3dInterface p0 = CoordInEarth(earthposition, radiusEarth, angleX, angleY);
            Vector3dInterface tip = CoordInEarth(earthposition, radiusEarth+1, angleX, angleY);

            double velocity = 0;
            //minimum velocity to leave earth
            while(velocity <= 17000){
                velocity = Math.random()*60000;
            }

            Vector3dInterface v0 = VelocityVector(velocity, tip, p0);

            ProbeSimulator p = new ProbeSimulator();

            trajectory = p.trajectory(p0, v0, maxtime, stepsize);
            solution = Score(trajectory, trajectoryTitan);
        };
        trajectorySolution = trajectory;
    }

        // angleX = [0,2pie) angleY = [0,pie)
        // could be wrong about my ranges
    public Vector3dInterface CoordInEarth(Vector3dInterface origin, double radius, double angleX, double angleY){
        double x = origin.getX()+radius*Math.sin(angleX)*Math.cos(angleY);
        double y = origin.getY()+radius*Math.sin(angleX)*Math.sin(angleY);
        double z = origin.getZ()+radius*Math.cos(angleX);
        return new Vector3d(x,y,z);
    }

    //tip is one meter above of tail so we know in which direction the velocity vector will need to go
    public Vector3dInterface VelocityVector(double speed, Vector3dInterface tip, Vector3dInterface tail){
        Vector3dInterface temp = tip.sub(tail);
        temp.mul(speed);
        return temp;
    }


    public Boolean Score(Vector3dInterface [] trajectory, Vector3dInterface [] trajectoryTitan){
        for(int x = 1; x< trajectory.length; x++){
            Vector3dInterface temp =trajectory[x].sub(trajectoryTitan[x]);
            double temp1 = temp.norm();
            if(temp1 <= radiusTitan){
                stepsToSolution = x;
                return true;
            }
        }
        return false;
    }
}
