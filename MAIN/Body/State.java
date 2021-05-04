package MAIN.Body;

import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;
import MAIN.Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class State implements StateInterface {

	public Vector3dInterface position;
	public Vector3dInterface velocity;
	public double mass;

	public LinkedList<PlanetBody> celestialBody;
	private LinkedList<Vector3d> prevAcceleration;

	public State(double mass, Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody, boolean flag) {
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		this.mass = mass;
		if(flag) celestialBody.add(new PlanetBody(mass, position, velocity));
	}

	/**
	 * Update a state to a new state computed by: this + step * rate
	 *
	 * @param step   The time-step of the update
	 * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
	 * @return The new state after the update. Required to have the same class as 'this'.
	 */
	public StateInterface addMul(double step, RateInterface rate) {

		LinkedList<Vector3d> acceleration = ((Rate) rate).getAcceleration();

		//Calculate two function
		//New position = old Position + stepSize * Velocity
		//New Velocity = old Velocity + stepSize * Rate (Acceleration)
		for (int i = 0; i < celestialBody.size(); i++) {
			PlanetBody planet = celestialBody.get(i);
			planet.setVelocity((Vector3d) celestialBody.get(i).getVelocity().addMul(step, acceleration.get(i)));
			planet.setPosition((Vector3d) celestialBody.get(i).getPosition().addMul(step, planet.getVelocity()));

			if(i == celestialBody.size()-1){
				this.position = planet.getPosition();
				this.velocity = planet.getVelocity();
			}
		}

		return this;
	}

	public StateInterface addMulVerlet(double step, RateInterface rate, ODEFunctionInterface f) {

		LinkedList<Vector3d> accelerationVector = ((Rate) rate).getAcceleration();

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
			planet.setVelocity((Vector3d) celestialBody.get(i).getVelocity().addMul(step/2.0, accelerationVector.get(i)));

			//New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
			planet.setPosition((Vector3d) celestialBody.get(i).getPosition().addMul(step, planet.getVelocity()));


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
			planet.setVelocity((Vector3d) celestialBody.get(i).getVelocity().addMul(step/2.0, accelerationVector.get(i)));

			if(i == celestialBody.size()-1){
				this.velocity = planet.getVelocity();
			}
		}

		return this;
	}


	public String toString() {
		return ("Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString());
	}

	public State clone(StateInterface x){
		State z = (State) x;
		LinkedList<PlanetBody> cloneplanets = new LinkedList<>();
		for (int i = 0; i < z.celestialBody.size(); i++) {
			PlanetBody object = z.celestialBody.get(i);
			Vector3d pos = object.getPosition();
			Vector3d vel = object.getVelocity();
			double mass = object.getM();
			cloneplanets.add(new PlanetBody(mass, pos, vel));
		}
		return new State(z.mass, z.position, z.velocity, cloneplanets, false);
	}
}