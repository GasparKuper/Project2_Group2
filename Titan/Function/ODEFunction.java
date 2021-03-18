package Titan.Function;


import Titan.Body.PlanetBody;
import Titan.Body.Vector3d;
import Titan.Interfaces.ODEFunctionInterface;
import Titan.Interfaces.RateInterface;
import Titan.Interfaces.StateInterface;
import Titan.Interfaces.Vector3dInterface;

import java.util.LinkedList;

public class ODEFunction implements ODEFunctionInterface {

	public State previousState;
	public Vector3dInterface velocity;
	public Vector3dInterface position;
	public double t;
	public RateInterface rate;

	public ODEFunction(Vector3dInterface velocity,Vector3dInterface position){
		this.velocity = velocity;
		this.position = position;
		this.previousState = new State(this.velocity,this.position);
		this.t = 0;
	}
	/*
	 * This is an interface for the function f that represents the
	 * differential equation dy/dt = f(t,y).
	 * You need to implement this function to represent to the laws of physics.
	 *
	 * For example, consider the differential equation
	 *   dy[0]/dt = y[1];  dy[1]/dt=cos(t)-sin(y[0])
	 * Then this function would be
	 *   f(t,y) = (y[1],cos(t)-sin(y[0])).
	 *
	 * @param   t   the time at which to evaluate the function
	 * @param   y   the state at which to evaluate the function
	 * @return  The average rate-of-change over the time-step. Has dimensions of [state]/[time].
	 */
	public RateInterface call(double t, StateInterface y){

		State yState = (State) y;
		Vector3dInterface vRate =(yState.velocity.add(previousState.velocity)).mul((1/(t-this.t)));
		Vector3dInterface pRate =(yState.position.add(previousState.position)).mul((1/(t-this.t)));
		rate = new Rate(pRate,vRate);
		previousState = yState;
		this.t = t;

		return rate;
	}
}
