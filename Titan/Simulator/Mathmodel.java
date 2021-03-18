package Titan;

public class Mathmodel {

    public void bruteforce(){
        //iterate through time from lowest to highest possible time
        //sim path of titan with t time
        //get the coord of titan at t time
        //find angles of direction vector
        //find location in earth to match the direction vector
        //iterate around the direction vector with a range of one unit degree higher or lower
        //fire rocket with x speed from location
        //get last position in trajectory
        //evaluate position in relation to the position of titan


    }

    public Vector3dInterface CoordInEarth(Vector3dInterface origin, double radius, double angleX, double angleY){
        double x = origin.getX()+radius*Math.sin(angleY)*Math.cos(angleX);
        double y = origin.getY()+radius*Math.sin(angleX)*Math.sin(angleY);
        double z = origin.getZ()+radius*Math.cos(angleX);
        return new Vector3d(x,y,z);
    }


    public double Score(Vector3dInterface [] projection){
        //score is distance between the probe final location and titan;
        return 0.0;
    }
}
