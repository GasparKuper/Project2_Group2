package MAIN.NWGravitation;

import MAIN.Body.Data;
import MAIN.Body.PlanetBody;


public class Force {

    public final static double G = 6.674e-11;

    public static void main(String[] args) {

        Data data = new Data();

        PlanetBody[] object = data.SolarSystem();

        double[] totalForce = new double[object.length * (object.length-1)];

        /*
        Probably not correct
        for (int i = 1; i < object.length; i++) {
            totalForce[i-1] = force(object[0], object[i]);
        }
         */

        int point = 0;
        for (PlanetBody body1 : object) {
            for (PlanetBody body2 : object) {
                if (body1 != body2) {
                    totalForce[point++] = ForceBetween(body1, body2);
                }
            }
        }
    }

    public static double ForceBetween(PlanetBody one, PlanetBody other){
        return (G * one.getM() * other.getM() / Math.pow(one.getVelocity().dist(other.getVelocity()), 2));
    }
    
      /*
      /*
            Finding force in 3d
            find vector distance between both masses
            distance vector between two bodies= |x,y,z|
            F= c( xi + yj + zk)
            F(force)
            c(coefficient)
            i,j,k(direction)

            |F| = √(x^2c^2 + y^2c^2 + z^2c^2)
            c = |F|/ √(x^2 + y^2 + z^2)
            F= |xc, yc, zc|

            adding all vectors
            |x,y,z| + |i,j,k| = |x+i, y+j, z+k|
     */

    public Vector3d forceVectorOnProbe(Object probe){

        Vector3d force = new Vector3d(0,0,0);

        Data data = new Data();
        PlanetBody[] object = data.SolarSystem();

        for(PlanetBody planet: object){
            Vector3d positionofplanet = planet.getPosition();

            //still need to implemment the probe
            double probeX = 0;
            double probeY = 0;
            double probeZ = 0;

            Vector3d distanceBetween = new Vector3d(positionofplanet.getX()-probeX, positionofplanet.getY()-probeY, positionofplanet.getZ()-probeZ);

            double result = ForceBetween(planet, probe);
            double coefficient = result / Math.sqrt(Math.pow(distanceBetween.getX(),2) + Math.pow(distanceBetween.getY(),2) + Math.pow(distanceBetween.getZ(),2));

            Vector3d temp = new Vector3d(distanceBetween.getX()*coefficient, distanceBetween.getY()*coefficient, distanceBetween.getZ()*coefficient);

            force.add(temp);
        }

        return force;
    }

}
