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

		planets[11] = new PlanetBody(massProbe, y.position, y.velocity);

		LinkedList<Vector3d> positions = new LinkedList<>();

		LinkedList<Vector3d> velocity = new LinkedList<>();

		for(PlanetBody planet : planets) {

			//TODO check Version 1
            /*
            double[] motion = motion_X_Y_Z(planet);
            double[] force = forceMotion_X_Y_Z(planet);
            positions.add(new Vector3d(h * motion[0], h * motion[1], h * motion[2]));
            velocity.add(new Vector3d(h * force[0], h * force[1], h * force[2]));
            */
			//TODO check Version 2
			double[] force = forceMotion_X_Y_Z(planet);

			velocity.add(new Vector3d(h * (force[0]/planet.getM()), h * (force[1]/planet.getM()), h * (force[2]/planet.getM())));
			positions.add(new Vector3d(h * planet.getVelocity().getX(), h * planet.getVelocity().getY(), h * planet.getVelocity().getZ()));
		}


		int point = 0;
		for(int i = 0; i < planets.length-1; i++){
			planets[i].setPosition((Vector3d) planets[i].getPosition().add(positions.get(point)));
			planets[i].setVelocity((Vector3d) planets[i].getVelocity().add(velocity.get(point)));
			point++;
		}
		return new Rate(positions.getLast(), velocity.getLast());
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
}
