package Run.CalculationForProbe;


import Body.Vector.Vector3d;
import static Constant.Constant.G;


public class OrbitPlanet {

    public Vector3d orbitSpeed(Vector3d posiProbe, Vector3d posiPlanet){

        final double massTitan = 1.34553e23;

        double distance = posiPlanet.dist(posiProbe);

//        Vector3d e1 = posiProbe;
//
//        Vector3d e2 = (Vector3d) posiProbe.mul(distance);
//
//        Vector3d n1 = e1.Normalize();
//
//        Vector3d n2 = e2.Normalize();
//
        double omega = 70;
//
//        double x = Math.cos(omega) * n1.getX() + Math.sin(omega) * n2.getX();
//        double y = Math.cos(omega) * n1.getY() + Math.sin(omega) * n2.getY();
//        double z = Math.cos(omega) * n1.getZ() + Math.sin(omega) * n2.getZ();

        double mu = G * massTitan;
        double orbitSpeed = Math.sqrt(mu/distance);

        double x = -orbitSpeed * Math.sin(omega);
        double y = orbitSpeed * Math.cos(omega);
        double z = 0;

        return new Vector3d(x, y, z);
    }
}
