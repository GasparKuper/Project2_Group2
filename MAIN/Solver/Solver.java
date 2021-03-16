package MAIN.Solver;

import MAIN.Body.Data;
import MAIN.Body.PlanetBody;
import MAIN.Body.SpaceShip;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.FunctionInterface;
import MAIN.Interfaces.SolverInterface;
import MAIN.Interfaces.Vector3dInterface;

import java.util.ArrayList;

public class Solver implements SolverInterface {

    public final static double G = 6.67408e-11;

     /*
        ONE STEP TODO one step == one second

        Acceleration == a
        Velocity == v
        Position == x
        Mass = m

        Force := G * m0*m / r^2

        a := G * M0 * (x0-x) / r^3    TODO a:= Force/M  or a:= G * m0*m / (r^2) * m

        //New velocity of the object
        v := v + T * a  TODO  y(t) = a*t + b

        //New position of the object
        x := x + T * v  TODO  y(t) = a*t + b

     */

    public PlanetBody[] planets;

    public SpaceShip spaceShip;


    public Solver(){
        Data data = new Data();

        this.planets = data.SolarSystem();

        FunctionInterface f = new Function();

        this.spaceShip = new SpaceShip(100,
                new Vector3d(0, 0, 0), //Position
                new Vector3d(0, 0, 0)); //Velocity


        solve(f, spaceShip.getPosition(), 0.1, 366*10 );
    }

    /*
     * Update rule for multiple steps. Time will start at zero.
     *
     * @param   f       the differential equation as defined in the project manual:
     *                  y(t) describes the position of the system at time t
     *                  f(t, y(t)) describes the derivative of y(t) with respect to time t
     * @param   x0      the starting location
     * @param   h       the step size in seconds
     * @param   nSteps  the total number of time steps
     * @return  an array of size nSteps with all intermediate locations along the path
     *
     */
    @Override
    public Vector3dInterface[] solve(FunctionInterface f, Vector3dInterface x0, double h, int nSteps) {

        ArrayList<Vector3dInterface> array = new ArrayList<>();

        initialMomenta();

        for (int i = 0; i < nSteps; i++) {

            array.add(step(f, i, x0, h));

            orbit(h); //Should be run at the same time with SpaceShip (Not Final Version)

        }


        return (Vector3dInterface[]) array.toArray();
    }

    /*
     * Update rule for one step.
     *
     * @param   f   the differential equation as defined in the project manual:
     *              y(t) describes the position of the system at time t
     *              f(t, y(t)) describes the derivative of y(t) with respect to time t
     * @param   t   the time
     * @param   x   the location
     * @param   h   the step size in seconds
     * @return  the new location after taking one step
     *
     */
    @Override
    public Vector3dInterface step(FunctionInterface f, double t, Vector3dInterface x, double h) {


        return null;
    }

    //@param   h   the step size in seconds
    private void orbit(double h){

        ArrayList<Vector3d> positions = new ArrayList<>();

        ArrayList<Vector3d> velocity = new ArrayList<>();

        //TODO Check
        /*
         TODO VERSION1
         q = position
         p = velocity                                      TODO VERSION 2
         fun_u = Newtons second law of motion              //New velocity of the object
         fun_v = Newtons laws of gravitation               v := v + T * a
                                                           //New position of the object
         SCILAB**                                          x := x + T * v

         q=q+h*fun_u(p);
         p=p+h*fun_v(q);
         vq=[vq,q];
         vp=[vp,q];

         TODO VERSION3 below
         */
        for(PlanetBody planet : planets) {

            double[] motion = motion_X_Y_Z(planet);
            double[] force = forceMotion_X_Y_Z(planet);

            positions.add(new Vector3d(h * motion[0], h * motion[1], h * motion[2]));

            velocity.add(new Vector3d(h * force[0], h * force[1], h * force[2]));
        }


        int point = 0;
        for(PlanetBody planetNextStep : planets){
            planetNextStep.getPosition().add(positions.get(point));
            planetNextStep.getVelocity().add(velocity.get(point));
            point++;
        }
    }

    //Newton's law of gravitation
    private double[] forceMotion_X_Y_Z(PlanetBody planet){

        double[] force = new double[3];
        for (PlanetBody planetBody : planets) {
            if (planet != planetBody) {
                force[0] += ForceX_Between(planet, planetBody);
                force[1] += ForceY_Between(planet, planetBody);
                force[2] += ForceZ_Between(planet, planetBody);
            }
        }
        return force;
    }

    public static double ForceX_Between(PlanetBody one, PlanetBody other) {
        return -(G * one.getM() * other.getM() *(one.getPosition().getX() - other.getPosition().getX())
                / Math.pow(Math.pow(one.getPosition().getX() - other.getPosition().getX(), 2), 3));
    }

    public static double ForceY_Between(PlanetBody one, PlanetBody other) {
        return -(G * one.getM() * other.getM() * (one.getPosition().getY() - other.getPosition().getY())
                / Math.pow(Math.pow(one.getPosition().getY() - other.getPosition().getY(), 2), 3));
    }

    public static double ForceZ_Between(PlanetBody one, PlanetBody other) {
        return -(G * one.getM() * other.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
                / Math.pow(Math.pow(one.getPosition().getZ() - other.getPosition().getZ(), 2), 3));
    }

    //Newton's second law of motion
    private double[] motion_X_Y_Z(PlanetBody planet){

        double[] motion = new double[3];

        motion[0] = planet.getVelocity().getX()/planet.getM();
        motion[1] = planet.getVelocity().getY()/planet.getM();
        motion[2] = planet.getVelocity().getZ()/planet.getM();

        return motion;
    }

    //Initial momenta
    private void initialMomenta(){
        for (PlanetBody planet : planets) {
            planet.getPosition().mul(planet.getM());
        }
    }

    public static void main(String[] args) {
        new Solver();
    }
}
