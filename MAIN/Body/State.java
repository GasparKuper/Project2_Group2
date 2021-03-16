import titan.*;

public class State implements StateInterface {

	private Vector3dInterface position;
	private Vector3dInterface velocity;

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
    	Rate newRate = (Rate) rate;
    	this.position = this.position.addMul(step, newRate.getPRate());
    	this.velocity = this.velocity.addMul(step, newRate.getVRate());
    	return this;
    }

    public String toString() {
    	return ("Position: " + this.position.toString() + "\nVelocity: " + this.velocity.toString());
    }
}