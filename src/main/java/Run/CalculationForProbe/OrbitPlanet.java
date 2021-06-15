package Run.CalculationForProbe;

import Body.Planets.Data;
import Body.Planets.PlanetBody;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.ODESolverInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;

import java.util.LinkedList;

public class OrbitPlanet {

    public Vector3d orbitSpeed(Vector3d posiProbe, Vector3d posiPlanet){
        Vector3d distance = (Vector3d) posiPlanet.sub(posiProbe);
        distance.Normalize();

        double x = 1;
        double y = 1;
        double z = -(distance.getX()*x + distance.getY()*y)/(-distance.getZ());

        Vector3d direction= new Vector3d(x,y,z);

        double velocity = Math.sqrt((1.34553e23*6.67408e-11)/distance.norm());
        direction = direction.Normalize();
        direction = (Vector3d) direction.mul(velocity);

        return direction;
    }

    public static void main(String[] args) {

        Vector3dInterface probe_relative_position = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3dInterface probe_relative_velocity = new Vector3d(5123.761101742686,-19016.061777347,-1210.1771491269928);

        State launchPosition = new State(probe_relative_position, probe_relative_velocity, new Data().getPlanets(), true);
        ODESolverInterface simulator = new ODESolver();


        State[] state = (State[]) simulator.solve(new ODEFunction(), launchPosition, 61670000, 600);
        int length = state.length - 1;

        Vector3d orbitVelocity = new OrbitPlanet().orbitSpeed((Vector3d) state[length].position, state[length].celestialBody.get(8).getPosition());

        System.out.println(orbitVelocity);
        System.out.println(orbitVelocity.norm());
    }
}
