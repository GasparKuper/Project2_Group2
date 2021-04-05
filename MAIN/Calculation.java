package MAIN;

import MAIN.Body.Vector3d;
import MAIN.ODESolver.ProbeSimulator;

public class Calculation extends Thread {

	private final double stepSize = 8639.3442623;
	private int time = 1;

	private Orbit sun;
	private Orbit mercury;
	private Orbit venus;
	private Orbit earth;
	private Orbit moon;
	private Orbit mars;
	private Orbit jupiter;
	private Orbit saturn;
	private Orbit titan;
	private Orbit uranus;
	private Orbit neptune;

	public Calculation(Orbit sun, Orbit merc, Orbit venus, Orbit earth, Orbit moon, Orbit mars, Orbit jupit, Orbit sat, Orbit titan, Orbit ur, Orbit nept) {
		this.sun = sun;
		this.mercury = merc;
		this.venus = venus;
		this.earth = earth;
		this.moon = moon;
		this.mars = mars;
		this.jupiter = jupit;
		this.saturn = sat;
		this.titan = titan;
		this.uranus = ur;
		this.neptune = nept;
	}

	public void run() {
		ProbeSimulator simulator = new ProbeSimulator();
		simulator.trajectory(new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
				new Vector3d(27978.003182957942, -62341.39349461967 ,-651.590970913659), 3.162e+7, 863.93442623);

	}
}