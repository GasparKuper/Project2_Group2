package MAIN.ODESolver;

import MAIN.Body.PlanetBody;
import MAIN.Body.Rate;
import MAIN.Body.State;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;

import java.util.LinkedList;

public class ODEFunction implements ODEFunctionInterface {

	private final static double G = 6.67408e-11;

	private LinkedList<PlanetBody> solarSystem;

	/**
	 * Calculating acceleration
	 * -G * Mass of other object * (position of the object - position of other objects)
	 * --------------------------------------------------------------------------------  DIVIDE
	 *  The vector distance between an object and another object in the third degree
	 *
	 * @param   t   the time at which to evaluate the function
	 * @param   y   the state at which to evaluate the function
	 * @return  The rate with the acceleration of each planets and probe.
	 */
	public RateInterface call(double t, StateInterface y) {
		LinkedList<Vector3d> accelerationBodies = new LinkedList<>();

		this.solarSystem = ((State) y).celestialBody;


		//Calculating acceleration
		// -G * Mass of other object * (position of the object - position of other objects)
		// --------------------------------------------------------------------------------  DIVIDE
		//  The vector distance between an object and another object in the third degree
		for (int i = 0; i < solarSystem.size(); i++) {
			Vector3d accel = new Vector3d(0, 0, 0);
			for (int j = 0; j < solarSystem.size(); j++) {
				if(i != j) {
					Vector3d body1 = solarSystem.get(i).getPosition();
					Vector3d body2 = solarSystem.get(j).getPosition();
					Vector3d b1b2 = (Vector3d) body1.sub(body2);
					double tmp = b1b2.norm();
					double force = -G * solarSystem.get(j).getM()/ Math.pow(tmp, 3);
					Vector3d acc = (Vector3d) b1b2.mul(force);
					accel = (Vector3d) accel.add(acc);
				}
			}
			accelerationBodies.add(accel);
		}

		return new Rate(accelerationBodies);
	}

}
