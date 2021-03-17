package MAIN.ODESolver;

import MAIN.Interfaces.ProbeSimulatorInterface;
import MAIN.Interfaces.Vector3dInterface;

public class ProbeSimulator implements ProbeSimulatorInterface {

      public PlanetBody[] planets;

    public final static double G = 6.67408e-11;


    /*
     * Simulate the solar system, including a probe fired from Earth at 00:00h on 1 April 2020.
     *
     * @param   p0      the starting position of the probe, relative to the earth's position.
     * @param   v0      the starting velocity of the probe, relative to the earth's velocity.
     * @param   ts      the times at which the states should be output, with ts[0] being the initial time.
     * @return  an array of size ts.length giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre.
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        Vector3dInterface [] position = new Vector3dInterface[ts.length];
        position [0] = p0;

        PlanetBody probe = new PlanetBody(15000, p0, v0);

        Data data = new Data();
        this.planets = data.SolarSystem();

        //compute the full motion of the solar system to find the effects of the various planets on the probe

        for(int x = 1; x < ts.length; x++){
            orbit(ts[x]);
            double[] force = forceMotion_X_Y_Z(probe);
            probe.setVelocity(probe.getVelocity().add(new Vector3d(ts[x] * (force[0]/probe.getM()), ts[x] * (force[1]/probe.getM()), ts[x] * (force[2]/probe.getM()))));
            probe.setPosition(probe.getPosition().add(new Vector3d(ts[x] * probe.getVelocity().getX(), ts[x] * probe.getVelocity().getY(), ts[x] * probe.getVelocity().getZ())));
            position[x] = probe.getPosition();
        }
        return position;
    }

    /*
     * Simulate the solar system with steps of an equal size.
     * The final step may have a smaller size, if the step-size does not exactly divide the solution time range.
     *
     * @param   tf      the final time of the evolution.
     * @param   h       the size of step to be taken
     * @return  an array of size round(tf/h)+1 giving the position of the probe at each time stated,
     *          taken relative to the Solar System barycentre
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        double [] ts = new double[(int) (tf/h)];

        for(int x = 0; x < ts.length; x++){
            ts[x] = h*x;
        }

        return trajectory(p0, v0, ts);
    }

    public void orbit(double h){
        ArrayList<Vector3d> positions = new ArrayList<>();

        ArrayList<Vector3d> velocity = new ArrayList<>();

        int count = 0;
        for(PlanetBody planet : planets) {

            double[] force = forceMotion_X_Y_Z(planet);

            velocity.add(new Vector3d(h * (force[0]/planet.getM()), h * (force[1]/planet.getM()), h * (force[2]/planet.getM())));
            positions.add(new Vector3d(h * planet.getVelocity().getX(), h * planet.getVelocity().getY(), h * planet.getVelocity().getZ()));
            count++;
        }


        int point = 0;
        for(int i = 0; i < planets.length; i++){
            planets[i].setPosition((Vector3d) planets[i].getPosition().add(positions.get(point)));
            planets[i].setVelocity((Vector3d) planets[i].getVelocity().add(velocity.get(point)));
            point++;
        }
    }

    // (g*m1*m2)/r^2=f
    private double[] forceMotion_X_Y_Z(PlanetBody planet){

        double [] force = new double[3];
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
                / Math.pow(Math.pow(one.getPosition().getX() - other.getPosition().getX(), 2), 2));
    }

    public static double ForceY_Between(PlanetBody one, PlanetBody other) {
        return -(G * one.getM() * other.getM() * (one.getPosition().getY() - other.getPosition().getY())
                / Math.pow(Math.pow(one.getPosition().getY() - other.getPosition().getY(), 2), 2));
    }

    public static double ForceZ_Between(PlanetBody one, PlanetBody other) {
        return -(G * one.getM() * other.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
                / Math.pow(Math.pow(one.getPosition().getZ() - other.getPosition().getZ(), 2), 2));
    }

}
