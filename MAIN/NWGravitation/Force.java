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

     */


    //
    public Vector3d forceVector(PlanetBody one, PlanetBody two){


        return null;
    }

}
