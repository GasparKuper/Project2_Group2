package MAIN.Body;

import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class Rate implements RateInterface {

	private LinkedList<Vector3d> acceleration;

	public Rate(LinkedList<Vector3d> acceleration) {
		this.acceleration = acceleration;
	}

	public LinkedList<Vector3d> getAcceleration() {
		return acceleration;
	}
}