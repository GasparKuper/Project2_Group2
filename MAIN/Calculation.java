package MAIN;

import MAIN.Body.Data;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.StateInterface;
import MAIN.ODESolver.Orbits;

public class Calculation extends Thread {

	private final double stepSize = 863.93442623;
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
	private Orbits solver;
	private StateInterface empty = new MAIN.Body.State(new Vector3d(0,0,0), new Vector3d(0,0,0));

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

		Data data = new Data();

		this.solver = new Orbits(data.getPlanets(), false);
	}

	public void run() {

		while (time < 36600) {

			solver.function(stepSize, empty);

			this.sun.setState(solver.planets[0].getPosition(), solver.planets[0].getVelocity());
			this.mercury.setState(solver.planets[1].getPosition(), solver.planets[1].getVelocity());
			this.venus.setState(solver.planets[2].getPosition(), solver.planets[2].getVelocity());
			this.earth.setState(solver.planets[3].getPosition(), solver.planets[3].getVelocity());
			this.moon.setState(solver.planets[4].getPosition(), solver.planets[4].getVelocity());
			this.mars.setState(solver.planets[5].getPosition(), solver.planets[5].getVelocity());
			this.jupiter.setState(solver.planets[6].getPosition(), solver.planets[6].getVelocity());
			this.saturn.setState(solver.planets[7].getPosition(), solver.planets[7].getVelocity());
			this.titan.setState(solver.planets[8].getPosition(), solver.planets[8].getVelocity());
			this.uranus.setState(solver.planets[9].getPosition(), solver.planets[9].getVelocity());
			this.neptune.setState(solver.planets[10].getPosition(), solver.planets[10].getVelocity());

			/*
			reset the scene to update their positions
			*/

			time++;
		}
	}
}