package MAIN.Body;

import MAIN.Interfaces.RateInterface;
import MAIN.Interfaces.StateInterface;
import MAIN.Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class State implements StateInterface {

	public Vector3dInterface position;
	public Vector3dInterface velocity;

	public LinkedList<PlanetBody> celestialBody;

	public State(double mass, Vector3dInterface position, Vector3dInterface velocity, LinkedList<PlanetBody> celestialBody) {
		this.position = position;
		this.velocity = velocity;
		this.celestialBody = celestialBody;
		celestialBody.add(new PlanetBody(mass, position, velocity));
	}

	public State(Vector3dInterface position, Vector3dInterface velocity) {
		this.position = position;
		this.velocity = velocity;
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

		for (int i = 0; i < celestialBody.size(); i++) {
			celestialBody.get(i).setPosition((Vector3d) celestialBody.get(i).getPosition().addMul(step, celestialBody.get(i).getVelocity()));
			celestialBody.get(i).setVelocity((Vector3d) celestialBody.get(i).getVelocity().addMul(step, acceleration.get(i)));
			if(i == celestialBody.size()-1){
				this.position = celestialBody.get(i).getPosition();
				this.velocity = celestialBody.get(i).getVelocity();
			}
		}
//		System.out.println(celestialBody.get(3).getPosition().toString());
		return this;
	}

	public String toString() {
		return ("Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString());
	}
}