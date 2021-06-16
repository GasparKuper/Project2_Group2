package Body.SpaceCrafts;

import Body.Planets.PlanetBody;
import Body.Solvers.ImplicitEuler;
import Body.Solvers.SymplecticEuler;
import Body.Solvers.VerletVelocity;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;

import java.util.LinkedList;

import static Constant.Constant.*;

/**
 * The state of the solar system
 */
public class State implements StateInterface {

	//Position of the Probe
	public Vector3dInterface position;

	//Velocity of the Probe
	public  Vector3dInterface velocity;

	//Mass of the probe
	private final double mass;

	//Fuel of the probe
	private final double fuel;

	//Lander
	private Lander lander;

	//Planets data of the solar system, also here a data of the probe index=11
	public LinkedList<PlanetBody> celestialBody;

	/**
	 * Constructor for the simulation of the solar system and the probe
	 * @param position Initial position of the probe
	 * @param velocity Initial velocity of the probe
	 * @param celestialBody Data of the planets in the solar system
	 * @param flag for cloning the object
	 */
	public State(Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody, boolean flag) {
		if(celestialBody == null)
			throw new RuntimeException("Data of the solar system is empty");

		this.mass = MASS;
		this.fuel = FUEL;
		this.lander = LANDER;
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		if(flag) celestialBody.add(new PlanetBody(getMass(), position, velocity));
	}

	/**
	 * For clone object
	 * Constructor for the simulation of the solar system and the probe
	 * @param position Initial position of the probe
	 * @param velocity Initial velocity of the probe
	 * @param celestialBody Data of the planets in the solar system
	 * @param flag for cloning the object
	 */
	public State(double mass, Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody, boolean flag, double fuel, Lander lander) {
		if(celestialBody == null)
			throw new RuntimeException("Data of the solar system is empty");

		this.lander = lander;
		this.fuel = fuel;
		this.mass = mass;
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		if(flag) celestialBody.add(new PlanetBody(getMass(), position, velocity));
	}

	/**
	 * Constructor for the solver only without planets of the solar system
	 * 	@param position Initial position of the probe
	 *  @param velocity Initial velocity of the probe
	 */
	public State(Vector3dInterface position, Vector3dInterface velocity) {

		this.mass = MASS;
		this.fuel = FUEL;
		this.lander = LANDER;
		this.celestialBody = new LinkedList<>();
		this.position = position;
		this.velocity = velocity;
		celestialBody.add(new PlanetBody(getMass(), position, velocity));
	}

	public StateInterface addMul(double step, RateInterface rate) {

		//Step
		if(SOLVER == 1) //Symplectic Euler Method
			return new SymplecticEuler().step(step, rate, this);
		else if(SOLVER == 2)//Implicit Euler Solver
			return new ImplicitEuler().step(step, rate, this);
		else if(SOLVER == 3) //Velocity-Verlet Solver
			return new VerletVelocity().step(step, rate, new ODEFunction(), this);

		return null;
	}

	/**
	 * Outputs of the position and velocity of the probe
	 * @return Position and Velocity of the probe
	 */
	public String toString() {
		return ("Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString());
	}

	/**
	 * Clones the object State
	 * @return new State object
	 */
	public State clone(){
		LinkedList<PlanetBody> cloneplanets = new LinkedList<>();

		for (int i = 0; i < celestialBody.size(); i++)
			cloneplanets.add(celestialBody.get(i).clone());

		return new State(mass, position, velocity, cloneplanets, false, fuel, getLander());
	}

	public Lander getLander() {
		return lander;
	}

	public double getMass() {
		return this.mass + this.fuel + this.lander.getMass() + this.lander.getFuel();
	}

	public double getFuel() {
		return this.fuel;
	}

	public void updateLander(Lander newLander) { this.lander = newLander; }
}
