package Body;

import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import Interfaces.Vector3dInterface;

import java.util.LinkedList;
import static Constant.Constant.FUEL;

public class State implements StateInterface {

	//Position of the Probe
	public Vector3dInterface position;

	//Velocity of the Probe
	public Vector3dInterface velocity;

	//Mass of the probe
	public double mass;

	//Fuel of the probe
	public double fuel;

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

	/**
	 * SYMPLECTIC EULER
	 * Updates position and velocity of each planets
	 * @param step   The time-step of the update
	 * @param rate   Acceleration array.
	 * @return The new state after the update. Required to have the same class as 'this'.
	 */
	public StateInterface addMul(double step, RateInterface rate) {

		LinkedList<Vector3d> acceleration = ((Rate) rate).getAcceleration();

		if(acceleration == null)
			throw new RuntimeException("Acceleration is empty");

		for (int i = 0; i < celestialBody.size(); i++) {
			PlanetBody planet = celestialBody.get(i);

			//New Velocity = old Velocity + stepSize * Rate (Acceleration)
			planet.setVelocity((Vector3d) planet.getVelocity().addMul(step, acceleration.get(i)));

			//New position = old Position + stepSize * Velocity
			planet.setPosition((Vector3d) planet.getPosition().addMul(step, planet.getVelocity()));

			if(i == celestialBody.size()-1){
				this.position = planet.getPosition();
				this.velocity = planet.getVelocity();
			}
		}

		return this;
	}

	/**
	 * IMPLICIT EULER
	 * Updates position and velocity of each planets
	 * @param step   The time-step of the update
	 * @param rate   Acceleration array.
	 * @return The new state after the update. Required to have the same class as 'this'.
	 */
	public StateInterface addMulImplicit(double step, RateInterface rate) {

		LinkedList<Vector3d> acceleration = ((Rate) rate).getAcceleration();

		if(acceleration == null)
			throw new RuntimeException("Acceleration is empty");

		for (int i = 0; i < celestialBody.size(); i++) {
			PlanetBody planet = celestialBody.get(i);

			//New position = old Position + stepSize * Velocity
			planet.setPosition((Vector3d) planet.getPosition().addMul(step, planet.getVelocity()));

			//New Velocity = old Velocity + stepSize * Rate (Acceleration)
			planet.setVelocity((Vector3d) planet.getVelocity().addMul(step, acceleration.get(i)));

			if(i == celestialBody.size()-1){
				this.position = planet.getPosition();
				this.velocity = planet.getVelocity();
			}
		}

		return this;
	}

	/**
	 * VELOCITY-VERLET (LEAPFROG INTEGRATION)
	 * Updates position and velocity of each planets
	 * @param step   The time-step of the update
	 * @param rate   Acceleration array.
	 * @return The new state after the update. Required to have the same class as 'this'.
	 */
	public StateInterface addMulVerletVelocity(double step, RateInterface rate, ODEFunctionInterface f) {

		LinkedList<Vector3d> accelerationVector = ((Rate) rate).getAcceleration();

		if(accelerationVector == null)
			throw new RuntimeException("Acceleration is empty");

		//Leapfrog Integration
		//New_Position(i+1) = old_position(i) + old_velocity(i)*step + 1/2 * (a(i))*step^2
		//New_Velocity(i+1) = old_velocity(i) + 1/2 * (a(i)+a(i+1))*step
		//
		//However, even in this synchronized form, the time-step. Delta t must be constant to maintain stability.[3]
		//
		//The synchronised form can be re-arranged to the 'kick-drift-kick' form;
		//New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
		//New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
		//New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
		//
		// which is primarily used where variable time-steps are required.
		// The separation of the acceleration calculation onto the beginning
		// and end of a step means that if time resolution is increased
		// by a factor of two, then only one extra (computationally expensive) acceleration calculation is required.

		for (int i = 0; i < celestialBody.size(); i++) {
			PlanetBody planet = celestialBody.get(i);


//			The synchronised form can be re-arranged to the 'kick-drift-kick' form;
			//New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
			planet.setVelocity((Vector3d) planet.getVelocity().addMul(step/2.0, accelerationVector.get(i)));

			//New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
			planet.setPosition((Vector3d) planet.getPosition().addMul(step, planet.getVelocity()));


			if(i == celestialBody.size()-1){
				this.position = planet.getPosition();
			}
		}

		//a(i+1)
		Rate function = (Rate) f.call(step, this);
		accelerationVector = function.getAcceleration();

		for (int i = 0; i < celestialBody.size(); i++) {
			PlanetBody planet = celestialBody.get(i);

			//New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
			planet.setVelocity((Vector3d) planet.getVelocity().addMul(step/2.0, accelerationVector.get(i)));

			if(i == celestialBody.size()-1){
				this.velocity = planet.getVelocity();
			}
		}

		return this;
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
		FUEL = this.fuel;

		for (int i = 0; i < celestialBody.size(); i++)
			cloneplanets.add(celestialBody.get(i).clone());

		return new State(mass, position, velocity, cloneplanets, false);
	}

	public void activateThruster(double consume, Vector3d direction){
		//v= v+(vex)ln(m0/m)
		double exhaustSpeed = 1.0;

		if(this.fuel >= consume){
			this.velocity = this.velocity.add(direction.mul(exhaustSpeed *Math.log((this.mass+(this.fuel-consume))/(this.mass+this.fuel))));
			this.celestialBody.get(11).setVelocity((Vector3d) this.velocity);
			this.fuel = this.fuel-consume;
		}else if(this.fuel > 0){
			this.fuel = 0;
			this.velocity = this.velocity.add(direction.mul(exhaustSpeed *Math.log((this.mass+(0))/(this.mass))));
			this.celestialBody.get(11).setVelocity((Vector3d) this.velocity);
		}
	}
}
