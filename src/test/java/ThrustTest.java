import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static Constant.Constant.FUEL;
import static org.junit.jupiter.api.Assertions.*;

public class ThrustTest {

    @Test
    @DisplayName("Thrust Test")
    public void thrustTest(){

        Vector3d p0 = new Vector3d(0, 0, 0);
        Vector3d v0 = new Vector3d(0, 0, 0);
        double mass = 78000;
        Vector3d exhaustVector = new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666);

        FUEL = 150;
        State probe = new State(mass, p0, v0);
        probe.activateThruster(100, exhaustVector);

        double consumeMax = 1.5;


        //Acceleration = (Vex * m(dot)) / mass of the probe
        Vector3d acceleration = (Vector3d) exhaustVector.mul(consumeMax/ (mass + FUEL));

        //Acceleration = acceleration / delta T
        acceleration = (Vector3d) acceleration.mul(100);

        v0 = (Vector3d) v0.add(acceleration);

        Vector3d final_vector = v0;

        assertEquals(final_vector.getX(), probe.velocity.getX());
        assertEquals(final_vector.getY(), probe.velocity.getY());
        assertEquals(final_vector.getZ(), probe.velocity.getZ());
    }
}
