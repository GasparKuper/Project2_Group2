package MAIN;

import MAIN.Interfaces.ODESolverInterface;
import MAIN.ODESolver.ODESolver;

public class Calculation extends Thread {

	private final double stepSize = 1.0;
	private double time = 1.0;

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
	private ODESolverInterface solver;

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

		this.solver = new ODESolver();
	}

	public void run() {

		while (time < 366) {
			this.sun.setState(this.solver.step(sun.getFunction(), time, sun.getState(), stepSize));
			this.mercury.setState(this.solver.step(mercury.getFunction(), time, mercury.getState(), stepSize));
			this.venus.setState(this.solver.step(venus.getFunction(), time, venus.getState(), stepSize));
			this.earth.setState(this.solver.step(earth.getFunction(), time, earth.getState(), stepSize));
			this.moon.setState(this.solver.step(moon.getFunction(), time, moon.getState(), stepSize));
			this.mars.setState(this.solver.step(mars.getFunction(), time, mars.getState(), stepSize));
			this.jupiter.setState(this.solver.step(jupiter.getFunction(), time, jupiter.getState(), stepSize));
			this.saturn.setState(this.solver.step(saturn.getFunction(), time, saturn.getState(), stepSize));
			this.titan.setState(this.solver.step(titan.getFunction(), time, titan.getState(), stepSize));
			this.uranus.setState(this.solver.step(uranus.getFunction(), time, uranus.getState(), stepSize));
			this.neptune.setState(this.solver.step(neptune.getFunction(), time, neptune.getState(), stepSize));

			/*
			reset the scene to update their positions
			*/

			time = time + stepSize;
		}
	}
}