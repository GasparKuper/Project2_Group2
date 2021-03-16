package MAIN.GeneticAlgorithm;

import MAIN.Body.Vector3d;
import MAIN.Interfaces.Vector3dInterface;

public class SurfacePosition {

    private final double radiusOfEarth = 6371000;

    private final Vector3d positionOfEarth = new Vector3d(-1.471922101663588e+11,  -2.860995816266412e+10, 8.278183193596080e+06);

    /**
     * @param altitude Sea-level
     * @param longitude east-west range from -180 to 180 degree
     * @param latitude north-south range from -90 to 90 degree
     * @return The position on the Earth
     */

    //https://gis.stackexchange.com/questions/23793/how-do-i-calculate-a-xyz-position-of-one-gps-position-relative-to-another
    public Vector3dInterface positionOnEarth(double altitude, double longitude, double latitude){

        double radius = radiusOfEarth + altitude;

        double x = radius * Math.cos(latitude) * Math.cos(longitude);

        double y = radius * Math.cos(latitude) * Math.sin(longitude);

        double z = radius * Math.sin(latitude);

        Vector3d pos = new Vector3d(x, y, z);

        return positionOfEarth.add(pos);
    }
}
