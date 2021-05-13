package Body;

import Interfaces.RateInterface;

import java.util.LinkedList;

public class Rate implements RateInterface {

	private LinkedList<Vector3d> acceleration;

	/**
	 * Constructor of the Rate
	 * @param acceleration Acceleration for each planets
	 */
	public Rate(LinkedList<Vector3d> acceleration) {
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