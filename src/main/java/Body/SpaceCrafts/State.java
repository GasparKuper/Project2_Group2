package Body.SpaceCrafts;

import Body.Planets.PlanetBody;
import Body.Solvers.ImplicitEuler;
import Body.Solvers.SymplecticEuler;
import Body.Solvers.VerletVelocity;
import Body.Vector.Vector3d;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;

import java.util.LinkedList;

import static Constant.Constant.FUEL;
import static Constant.Constant.SOLVER;

/**
 * The state of the solar system
 */
public class State implements StateInterface {

	//Position of the Probe
	public Vector3dInterface position;

	//Velocity of the Probe
	public Vector3dInterface velocity;

	//Mass of the probe
	public double mass;

	//Fuel of the probe
	public double fuel;

	//Lander
	private Lander lander;

	//Planets data of the solar system, also here a data of the probe index=11
	public LinkedList<PlanetBody> celestialBody;

	/**
	 * Constructor for the simulation of the solar system and the probe
	 * @param mass Initial Mass of the probe
	 * @param position Initial position of the probe
	 * @param velocity Initial velocity of the probe
	 * @param celestialBody Data of the planets in the solar system
	 * @param flag for cloning the object
	 */
	public State(double mass, Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody, boolean flag) {
		if(celestialBody == null)
			throw new RuntimeException("Data of the solar system is empty");

		this.fuel = FUEL;
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		this.mass = mass;
		if(flag) celestialBody.add(new PlanetBody(mass, position, velocity));
	}

	/**
	 * Constructor for the simulation of the solar system and the probe
	 * @param mass Initial Mass of the probe
	 * @param position Initial position of the probe
	 * @param velocity Initial velocity of the probe
	 * @param celestialBody Data of the planets in the solar system
	 * @param flag for cloning the object
	 * @param fuel fuel fo the probe
	 */
	public State(double mass, Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody, boolean flag, double fuel) {
		if(celestialBody == null)
			throw new RuntimeException("Data of the solar system is empty");

		this.fuel = fuel;
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		this.mass = mass;
		if(flag) celestialBody.add(new PlanetBody(mass, position, velocity));
	}

	/**
	 * Constructor for the solver only without planets of the solar system
	 *  @param mass Initial Mass of the probe
	 * 	@param position Initial position of the probe
	 *  @param velocity Initial velocity of the probe
	 */
	public State(double mass, Vector3dInterface position, Vector3dInterface velocity) {
		this.fuel = FUEL;
		this.celestialBody = new LinkedList<>();
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
		celestialBody.add(new PlanetBody(mass, position, velocity));
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

		return new State(mass, position, velocity, cloneplanets, false, fuel);
	}

	/**
	 * Thrust of the probe
	 * @param step step size
	 * @param thrusterVector the exhaust velocity vector
	 */
	public void activateThruster(double step, Vector3d thrusterVector){

		int l = celestialBody.size() - 1;

		//m(dot) Max = 30MN / 20km/s
		double consumeMax = 1.5;
		if (consumeMax*step <= this.fuel) {

			//Acceleration = (Vex * m(dot)) / mass of the probe
			Vector3d acceleration = (Vector3d) thrusterVector.mul(consumeMax/ (this.mass+this.fuel));

			//Acceleration = acceleration / delta T
			acceleration = (Vector3d) acceleration.mul(step);

			this.velocity = this.velocity.add(acceleration);
			this.celestialBody.get(l).setVelocity((Vector3d) this.velocity);

			//Change the fuel
			this.fuel = this.fuel - (consumeMax*step);
		}
	}

	public Lander getLander() {
		return lander;
	}

	public void setLander(Lander lander) {
		this.mass += lander.getMass() + lander.getFuel();
		this.lander = lander;
	}
}
