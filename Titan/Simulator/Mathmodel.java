package Titan;

public class Mathmodel {

   private static double stepsize = 863;
    private static double maxtime = 3.154e+7;
    private static double radiusEarth = 6378000;
    private static double radiusTitan = 2574700;
    private static Vector3dInterface earthposition = new Vector3d(-1.471922101663588e+11,  -2.860995816266412e+10, 8.278183193596080e+06);
    private static Vector3dInterface titanFinalposition = new Vector3d(1,1,1); //need to find position of titan after one year
    public Vector3dInterface [] trajectorySolution;

    public void bruteforce(){
        Boolean solution = false;
        Vector3dInterface[] trajectory;
        while(solution != true){
            double angleX = Math.random() * 360;
            double angleY = Math.random() * 180;

            Vector3dInterface p0 = CoordInEarth(earthposition, radiusEarth, angleX, angleY);
            Vector3dInterface tip = CoordInEarth(earthposition, radiusEarth+1, angleX, angleY);

            double velocity = 0;
            //minimum velocity to leave earth
            while(velocity <= 17000){
                velocity = Math.random()*60000;
            }

            ProbeSimulator p = new ProbeSimulator();

            Vector3dInterface v0 = VelocityVector(velocity, tip, p0);
            trajectory = p.trajectory(p0, v0, maxtime, stepsize);
            solution = Score(trajectory, titanFinalposition);
        }
        trajectorySolution = trajectory;
    }

        // angleX = [0,2pie) angleY = [0,pie)
    public Vector3dInterface CoordInEarth(Vector3dInterface origin, double radius, double angleX, double angleY){
        double x = origin.getX()+radius*Math.sin(angleY)*Math.cos(angleX);
        double y = origin.getY()+radius*Math.sin(angleX)*Math.sin(angleY);
        double z = origin.getZ()+radius*Math.cos(angleY);
        return new Vector3d(x,y,z);
    }

    //tip is one meter above of tail so we know in which direction your vector will need to go
    public Vector3dInterface VelocityVector(double speed, Vector3dInterface tip, Vector3dInterface tail){
        Vector3dInterface temp = tip.sub(tail);
        temp.mul(speed);
        return temp;
    }

    public double[] angleToTitan(Vector3dInterface earthpositon, Vector3dInterface titanposition ){
        double[] angles = new double[2];
        return angles;
    }


    public Boolean Score(Vector3dInterface [] trajectory, Vector3dInterface finalpositionTitan){
        Vector3dInterface temp =trajectory[trajectory.length-1].sub(finalpositionTitan);
        double temp1 = temp.norm();
        if(temp1 <= radiusTitan){
            return true;
        }else {
            return false;
        }
    }
}
