package Body.Solvers;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import ODESolver.Function.Rate;

import java.util.LinkedList;

public class ImplicitEuler {

    /**
     * IMPLICIT EULER
     * Updates position and velocity of each planets
     * @param step   The time-step of the update
     * @param rate   Acceleration array.
     * @return The new state after the update. Required to have the same class as 'this'.
     */
    public StateInterface step(double step, RateInterface rate, State state) {

        LinkedList<Vector3d> acceleration = ((Rate) rate).getAcceleration();

        if(acceleration == null)
            throw new RuntimeException("Acceleration is empty");

        for (int i = 0; i < state.celestialBody.size(); i++) {
            PlanetBody planet = state.celestialBody.get(i);

            //New position = old Position + stepSize * Velocity
            planet.setPosition((Vector3d) planet.getPosition().addMul(step, planet.getVelocity()));

            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            planet.setVelocity((Vector3d) planet.getVelocity().addMul(step, acceleration.get(i)));

            if(i == state.celestialBody.size()-1){
                state.position = planet.getPosition();
                state.velocity = planet.getVelocity();
            }
        }

        return state;
    }
}
