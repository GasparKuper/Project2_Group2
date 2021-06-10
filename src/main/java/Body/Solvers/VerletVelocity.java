package Body.Solvers;

import Body.Planets.PlanetBody;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.ODEFunctionInterface;
import Interfaces.RateInterface;
import Interfaces.StateInterface;
import ODESolver.Function.Rate;

import java.util.LinkedList;

public class VerletVelocity {

    /**
     * VELOCITY-VERLET (LEAPFROG INTEGRATION)
     * Updates position and velocity of each planets
     * @param step   The time-step of the update
     * @param rate   Acceleration array.
     * @return The new state after the update. Required to have the same class as 'this'.
     */
    public StateInterface step(double step, RateInterface rate, ODEFunctionInterface f, State state) {

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

        for (int i = 0; i < state.celestialBody.size(); i++) {
            PlanetBody planet = state.celestialBody.get(i);


//			The synchronised form can be re-arranged to the 'kick-drift-kick' form;
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            planet.setVelocity((Vector3d) planet.getVelocity().addMul(step/2.0, accelerationVector.get(i)));

            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            planet.setPosition((Vector3d) planet.getPosition().addMul(step, planet.getVelocity()));


            if(i == state.celestialBody.size()-1){
                state.position = planet.getPosition();
            }
        }

        //a(i+1)
        Rate function = (Rate) f.call(step, state);
        accelerationVector = function.getAcceleration();

        for (int i = 0; i < state.celestialBody.size(); i++) {
            PlanetBody planet = state.celestialBody.get(i);

            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
            planet.setVelocity((Vector3d) planet.getVelocity().addMul(step/2.0, accelerationVector.get(i)));

            if(i == state.celestialBody.size()-1){
                state.velocity = planet.getVelocity();
            }
        }

        return state;
    }
}
