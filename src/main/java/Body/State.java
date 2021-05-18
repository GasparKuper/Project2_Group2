package Body;

import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class State implements StateInterface {

	//Position of the Probe
	public Vector3dInterface position;

	//Velocity of the Probe
	public Vector3dInterface velocity;

	//Mass of the probe
	public double mass;

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


	//Flag for the first step for Stormer-Verlet
	private static boolean stormerFlag = true;
	//Previous data of the solar system (Step before)
	private static LinkedList<PlanetBody> prevCelestialBody;

	/**
	 * STORMER-VERLET
	 * Updates position and velocity of each planets
	 * @param step   The time-step of the update
	 * @param rate   Acceleration array.
	 * @return The new state after the update. Required to have the same class as 'this'.
	 */
	//Not yet implemented, need to fix
	public StateInterface addMulVerletStormer(double step, RateInterface rate, ODEFunctionInterface f) {
		//if flag=true, it means that we have first step of the stormer-verlet
		if(stormerFlag){
			prevCelestialBody = new LinkedList<>();

			for (int i = 0; i < celestialBody.size(); i++)
				prevCelestialBody.add(celestialBody.get(i).clone());

			//For the first step, we use Verlet-Velocity
			addMulVerletVelocity(step, rate, f);

			stormerFlag = false;
		} else {

			//Gets acceleration from the Rate
			LinkedList<Vector3d> accelerationVector = ((Rate) rate).getAcceleration();

			if(accelerationVector == null)
				throw new RuntimeException("Acceleration is empty");

			//Temporary previous data of the solar system
			LinkedList<PlanetBody> tmp_prevCelestialBody = new LinkedList<>();

			//Cloning
			for (int i = 0; i < celestialBody.size(); i++)
				tmp_prevCelestialBody.add(celestialBody.get(i).clone());

			//Update position and velocity
			for (int i = 0; i < celestialBody.size(); i++) {
				PlanetBody planet = celestialBody.get(i);

				Vector3d acceleration = accelerationVector.get(i);

				//New_position = old_position*2 - previous_position + acceleration * step^2
				Vector3d tmp_pos = (Vector3d) planet.getPosition().mul(2);
				tmp_pos = (Vector3d) tmp_pos.sub(prevCelestialBody.get(i).getPosition());
				Vector3d tmp_acc = (Vector3d) acceleration.mul(step*step);
				tmp_pos = (Vector3d) tmp_pos.add(tmp_acc);
				planet.setPosition(tmp_pos);

				//New_Velocity = old_velocity + acceleration * step
				planet.setVelocity((Vector3d) planet.getVelocity().addMul(step, acceleration));

				if(i == celestialBody.size()-1){
					this.position = planet.getPosition();
					this.velocity = planet.getVelocity();
				}
			}

			prevCelestialBody = tmp_prevCelestialBody;
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
		LinkedList<PlanetBody> cloneplanets = new LinkedList<>()
				;
		for (int i = 0; i < celestialBody.size(); i++)
			cloneplanets.add(celestialBody.get(i).clone());

		return new State(mass, position, velocity, cloneplanets, false);
	}
}