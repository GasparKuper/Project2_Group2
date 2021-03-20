package MAIN.ODESolver;

import MAIN.Body.PlanetBody;
import MAIN.Body.Rate;
import MAIN.Body.State;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;
import MAIN.UI;

import java.util.LinkedList;

public class ODEFunction implements ODEFunctionInterface {

	private final static double G = 6.67408e-11;

	private LinkedList<PlanetBody> solarSystem;

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
	public RateInterface call(double t, StateInterface y) {
		LinkedList<Vector3d> accelerationBodies = new LinkedList<>();

		this.solarSystem = ((State) y).celestialBody;

		for (int i = 0; i < solarSystem.size(); i++) {
			Vector3d accel = new Vector3d(0, 0, 0);
			for (int j = 0; j < solarSystem.size(); j++) {
				if(i != j) {
					Vector3d body1 = new Vector3d(0, 0, 0);
					Vector3d body2 = new Vector3d(0, 0, 0);
					body1 = (Vector3d) body1.add(solarSystem.get(i).getPosition());
					body2 = (Vector3d) body2.add(solarSystem.get(j).getPosition());
					double distance = body1.dist(body2);
					Vector3d b1b2 = (Vector3d) body1.sub(body2);
					Vector3d acc = (Vector3d) b1b2.mul(-G * solarSystem.get(j).getM() / Math.pow(distance, 3));
					accel = (Vector3d) accel.add(acc);
				}
			}
			accelerationBodies.add(accel);
		}

		// System.out.println(planets[8].getPosition().toString());

		return new Rate(accelerationBodies);
	}

}
