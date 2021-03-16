package MAIN.Body;

import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.Vector3dInterface;

public class Rate implements RateInterface {

	private Vector3dInterface vRate;
	private Vector3dInterface pRate;

	public Rate(Vector3dInterface velocity, Vector3dInterface acceleration) {
		this.vRate = acceleration;
		this.pRate = velocity;
	}

	public Vector3dInterface getPRate() {
		return this.pRate;
	}

	public Vector3dInterface getVRate() {
		return this.vRate;
	}

}