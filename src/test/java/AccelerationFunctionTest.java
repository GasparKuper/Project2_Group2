import Body.Planets.PlanetBody;
import ODESolver.Function.Rate;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.RateInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static Constant.Constant.G;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccelerationFunctionTest {

    @Test
    @DisplayName("Acceleration function without celestials Body")
    public void AccelerationFunctionWithoutCelestialsBody(){
        Rate acceleration = (Rate) accelerationWithoutCelestialBody();
        LinkedList<Vector3d> accelerationArray = acceleration.getAcceleration();
        Vector3d check = new Vector3d(0, 0, 0);
        Vector3d accelerationProbe = accelerationArray.get(0);
        assertEquals(accelerationArray.size(), 1);
        assertEquals(accelerationProbe.getX(), check.getX());
        assertEquals(accelerationProbe.getY(), check.getY());
        assertEquals(accelerationProbe.getZ(), check.getZ());
    }

    private RateInterface accelerationWithoutCelestialBody(){
        State probe = new State(15000, new Vector3d(0, 0, 0), new Vector3d(100, 0, 0));
        ODEFunction odeFunction = new ODEFunction();
        return odeFunction.call(5, probe);
    }

    @Test
    @DisplayName("Acceleration function with the Earth")
    public void AccelerationFunctionWithCelestialsBody(){
        Rate acceleration = (Rate) accelerationWithCelestialBody();
        LinkedList<Vector3d> accelerationArray = acceleration.getAcceleration();
        Vector3d probeByHand = probeByHand();
        Vector3d earthByHand = earthByHand();
        Vector3d accelerationProbe = accelerationArray.get(1);
        Vector3d accelerationEarth = accelerationArray.get(0);
        assertEquals(accelerationArray.size(), 2);
        assertEquals(accelerationProbe.getX(), probeByHand.getX());
        assertEquals(accelerationProbe.getY(), probeByHand.getY());
        assertEquals(accelerationProbe.getZ(), probeByHand.getZ());
        assertEquals(accelerationEarth.getX(), earthByHand.getX());
        assertEquals(accelerationEarth.getY(), earthByHand.getY());
        assertEquals(accelerationEarth.getZ(), earthByHand.getZ());
    }

    private RateInterface accelerationWithCelestialBody(){
        //Probe
        Vector3dInterface p0 = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3dInterface v0 = new Vector3d(35760.650634765625,-48159.48486328125,-604.095458984375);

        LinkedList<PlanetBody> solarSystem = new LinkedList<>();
        //Earth
        solarSystem.add(new PlanetBody(5.97219e24,
                new Vector3d(-147192316663.54238892,  -28610002992.464771271, 8291942.4644112586975),
                new Vector3d(5427.1933760188148881, -29310.566234715199244, 0.65751148935788705785)));

        State state = new State(15000, p0.add(solarSystem.get(0).getPosition()),
                v0.add(solarSystem.get(0).getVelocity()), solarSystem, true);

        ODEFunction odeFunction = new ODEFunction();

        return odeFunction.call(5, state);
    }

    //Calculating acceleration
    // -G * Mass of other object * (position of the object - position of other objects)
    // --------------------------------------------------------------------------------  DIVIDE
    //  The vector distance between an object and another object in the third degree
    private Vector3d probeByHand(){
        Vector3d pos_Probe = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3d pos_Earth = new Vector3d(-147192316663.54238892,  -28610002992.464771271, 8291942.4644112586975);
        pos_Probe = (Vector3d) pos_Probe.add(pos_Earth);

        Vector3d b1b2 = (Vector3d) pos_Probe.sub(pos_Earth);
        double tmp = b1b2.norm();
        double force = -G * 5.97219e24/ Math.pow(tmp, 3);
        return (Vector3d) b1b2.mul(force);
    }

    private Vector3d earthByHand(){
        Vector3d pos_Probe = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3d pos_Earth = new Vector3d(-147192316663.54238892,  -28610002992.464771271, 8291942.4644112586975);
        pos_Probe = (Vector3d) pos_Probe.add(pos_Earth);

        Vector3d b1b2 = (Vector3d) pos_Earth.sub(pos_Probe);
        double tmp = b1b2.norm();
        double force = -G * 15000/ Math.pow(tmp, 3);
        return (Vector3d) b1b2.mul(force);
    }
}
