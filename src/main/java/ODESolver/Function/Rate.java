package ODESolver.Function;

import Body.Vector.Vector3d;
import Interfaces.RateInterface;

import java.util.LinkedList;

/**
 * Rate class for storing acceleration
 */
public class Rate implements RateInterface {

	private LinkedList<Vector3d> acceleration;

	/**
	 * Constructor of the Rate
	 * @param acceleration Acceleration for each planets
	 */
	public Rate(LinkedList<Vector3d> acceleration) {
		if(acceleration == null)
			throw new RuntimeException("Acceleration is empty");
		this.acceleration = acceleration;
	}

	/**
	 * Gets array of the acceleration
	 * @return array of the acceleration
	 */
	public LinkedList<Vector3d> getAcceleration() {
		return acceleration;
	}
}