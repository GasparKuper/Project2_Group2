package MAIN.ODESolver;

import MAIN.Body.PlanetBody;
import MAIN.Body.State;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.StateInterface;
import MAIN.Orbit;
import MAIN.UI;

import java.util.LinkedList;

public class Orbits {

    private final static double G = 6.67408e-11;

    private final boolean flag;

    public final PlanetBody[] planets;

    private double mass;

    public Orbits(PlanetBody[] planets, double mass, boolean flag){
        this.planets = planets;
        this.mass = mass;
        this.flag = flag;
    }

    public Orbits(PlanetBody[] planets, boolean flag){
        this.flag = flag;
        this.planets = planets;
    }

    public StateInterface function(double h, StateInterface y){

        State tmp = (State) y;

        if((!Double.isNaN(tmp.velocity.getY()) || !Double.isNaN(tmp.velocity.getZ()) || !Double.isNaN(tmp.velocity.getZ())) && flag) {
            planets[11] = new PlanetBody(mass, tmp.position, tmp.velocity);
        }else {
        //    planets[11] = null;
        }

        LinkedList<Vector3d> positions = new LinkedList<>();

        LinkedList<Vector3d> velocity = new LinkedList<>();

        for(int i = 0; i < planets.length; i++) {
            if(planets[i] == null)
                continue;

                double[] force = forceMotion_X_Y_Z(planets[i]);
                positions.add(new Vector3d(h * planets[i].getVelocity().getX(), h * planets[i].getVelocity().getY(), h * planets[i].getVelocity().getZ()));
                velocity.add(new Vector3d(h * (force[0]), h * (force[1]), h * (force[2])));
        }

        for(int i = 0; i < planets.length; i++){
            if(planets[i] == null)
                continue;

            planets[i].setPosition((Vector3d) planets[i].getPosition().add(positions.get(i)));
            planets[i].setVelocity((Vector3d) planets[i].getVelocity().add(velocity.get(i)));
        }

       // System.out.println(planets[8].getPosition().toString());
        long ml = (long) 1.0;
        try{
            Thread.sleep(ml);
        }catch(InterruptedException e){}

        this.updatePosition();
        if(planets[11] == null) {
            Vector3d a = new Vector3d(0, 0, 0);
            return new State(a, a);
        }

        return new State(planets[11].getPosition(), planets[11].getPosition());
    }

    public void updatePosition() {
        UI f = new UI();
        System.out.println("Probe position : " +planets[11].getPosition().toString());
        System.out.println("Probe velocity : " +planets[11].getVelocity().toString());
        for(int i =0;i<f.getOrbit().length;i++) {
            f.getOrbit()[i].getShape().translateXProperty().set(planets[i].getPosition().getX() / 100000000);
            f.getOrbit()[i].getShape().translateYProperty().set(-planets[i].getPosition().getZ() / 100000000);
            f.getOrbit()[i].getShape().translateZProperty().set(planets[i].getPosition().getY() / 100000000);
        }
    }
    // (g*m1*m2)/r^2=f
    private double[] forceMotion_X_Y_Z(PlanetBody planet){

        double [] force = new double[3];
        for (int i = 0; i < planets.length; i++) {
            if(planets[i] == null)
                continue;

            if (planet != planets[i]) {
                force[0] += ForceX_Between(planet, planets[i]);
                force[1] += ForceY_Between(planet, planets[i]);
                force[2] += ForceZ_Between(planet, planets[i]);
            }
        }

        return force;
    }

    public static double ForceX_Between(PlanetBody one, PlanetBody other) {
        double r = one.getPosition().dist(other.getPosition());
        return -(G * other.getM() * ((one.getPosition().getX() - other.getPosition().getX())
                / Math.pow(r, 3)));
    }
    public static double ForceY_Between(PlanetBody one, PlanetBody other) {
        double r = one.getPosition().dist(other.getPosition());
        return -(G * other.getM() * (one.getPosition().getY() - other.getPosition().getY())
                / Math.pow(r, 3));
    }
    public static double ForceZ_Between(PlanetBody one, PlanetBody other) {
        double r = one.getPosition().dist(other.getPosition());
        return -(G * other.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
                / Math.pow(r, 3));
    }
}