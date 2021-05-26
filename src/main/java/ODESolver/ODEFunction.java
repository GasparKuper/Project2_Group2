package ODESolver;

import Body.PlanetBody;
import Body.Rate;
import Body.State;
import Body.Vector3d;
import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;

import java.util.LinkedList;

import static Constant.Constant.FLAG_VERLET_TEST;
import static Constant.Constant.G;

public class ODEFunction implements ODEFunctionInterface {

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

		LinkedList<PlanetBody> solarSystem = ((State) y).celestialBody;

		if(solarSystem == null)
			throw new RuntimeException("Solar system is empty");


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
			if(Double.isNaN(accel.getX()) ||
					Double.isNaN(accel.getY()) ||
						Double.isNaN(accel.getZ())){
				throw new RuntimeException("Acceleration goes in NaN");
			}
			accelerationBodies.add(accel);
		}

		if(FLAG_VERLET_TEST){
			LinkedList<Vector3d> acc = new LinkedList<>();
			Vector3d accVector = new Vector3d(5, 0, 0);
			acc.add(accVector);
			return new Rate(acc);
		}

		return new Rate(accelerationBodies);
	}

}
