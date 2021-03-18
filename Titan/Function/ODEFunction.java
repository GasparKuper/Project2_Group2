package Titan.Function;


import Titan.Body.PlanetBody;
import Titan.Body.Vector3d;
import Titan.Interfaces.ODEFunctionInterface;
import Titan.Interfaces.RateInterface;
import Titan.Interfaces.StateInterface;

import java.util.ArrayList;
import java.util.LinkedList;

public class ODEFunction implements ODEFunctionInterface {

	private final static double G = 6.67408e-11;

	private final PlanetBody[] planets;

	private boolean flag = true;

	private final double massProbe;
	public ODEFunction(PlanetBody[] planets, double massProbe){
		this.planets = planets;
		this.massProbe = massProbe;
	}
	/*
	 * This is an interface for the function f that represents the
	 * differential equation dy/dt = f(t,y).
	 * You need to implement this function to represent to the laws of physics.
	 *
	 * For example, consider the differential equation
	 *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
	 * Then this function would be
	 *   f(t,y) = (y[1],cos(t)-sin(y[0])).
	 *
	 * @param   t   the time at which to evaluate the function
	 * @param   y   the state at which to evaluate the function
	 * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
	 */
	public RateInterface call(double t, StateInterface y){

		return function(t, (State) y);
	}

	public RateInterface function(double h, State y){


		LinkedList<Vector3d> positions = new LinkedList<>();

		LinkedList<Vector3d> velocity = new LinkedList<>();

		for(int i = 0; i < planets.length; i++) {
			double[] force = forceMotion_X_Y_Z(planets[i]);
			positions.add(new Vector3d(h * planets[i].getVelocity().getX(), h * planets[i].getVelocity().getY(), h * planets[i].getVelocity().getZ()));
			velocity.add(new Vector3d(h * (force[0]), h * (force[1]), h * (force[2])));
		}

		for(int i = 0; i < planets.length; i++){
			planets[i].setPosition((Vector3d) planets[i].getPosition().add(positions.get(i)));
			planets[i].setVelocity((Vector3d) planets[i].getVelocity().add(velocity.get(i)));
		}

		System.out.println(planets[3].getPosition().toString());
		return new Rate(positions.getLast(), positions.getLast());
	}

	// (g*m1*m2)/r^2=f
	private double[] forceMotion_X_Y_Z(PlanetBody planet){

		double [] force = new double[3];
		for (int i = 0; i < planets.length; i++) {
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
		return -(G * other.getM() * (one.getPosition().getX()- other.getPosition().getX())
				/ Math.pow(r, 3));
	}
	public static double ForceY_Between(PlanetBody one, PlanetBody other) {
		double r = one.getPosition().dist(other.getPosition());
		return -(G * other.getM() * (one.getPosition().getY()- other.getPosition().getY())
				/ Math.pow(r, 3));
	}
	public static double ForceZ_Between(PlanetBody one, PlanetBody other) {
		double r = one.getPosition().dist(other.getPosition());
		return -(G * other.getM() * (one.getPosition().getZ() - other.getPosition().getZ())
				/ Math.pow(r, 3));
	}

	//Newton's second law of motion
	private double[] motion_X_Y_Z(PlanetBody planet){

		double[] motion = new double[3];

		motion[0] = planet.getVelocity().getX()/planet.getM();
		motion[1] = planet.getVelocity().getY()/planet.getM();
		motion[2] = planet.getVelocity().getZ()/planet.getM();

		return motion;
	}

}
