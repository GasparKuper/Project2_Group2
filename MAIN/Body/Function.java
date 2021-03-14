package MAIN.Body;

import MAIN.Interfaces.*;

public class Function implements FunctionInterface {

	private final double xInit;
	private final double yInit;
	private final double zInit;

	public Function(double x, double y, double z) {
		this.xInit = x;
		this.yInit = y;
		this.zInit = z;
	}

	public Vector3dInterface call(double t, Vector3dInterface s) {
		Vector3dInterface result = new Vector3d((s.getX() - xInit) / t, (s.getY() - yInit) / t, (s.getZ() - zInit) / t);
		return result;
	}

}