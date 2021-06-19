import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;
import Controller.Phase2;
import Controller.Phase4;
import Controller.RotationPhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenLoopControllerTest {

    private final double accuracy = 0.1;


    @Nested
    @DisplayName("Acceleration by X coordinate")
    class xCoordinateThruster{

        @Test
        @DisplayName("1 seconds step size/30000 meters from goal")
        public void seconds30000meters(){
            Lander result = xAcceleration(1, 30000, -90);
            assertEquals(0, result.getPosition().getX(), accuracy);
            assertEquals(0, result.getVelocity().getX(), accuracy);
        }
        @Test
        @DisplayName("1 seconds step size/-30000 meters from goal")
        public void secondsMinus30000meters(){
            Lander result = xAcceleration(1, -30000, 90);
            assertEquals(0, result.getPosition().getX(), accuracy);
            assertEquals(0, result.getVelocity().getX(), accuracy);
        }

        @Test
        @DisplayName("0.1 seconds step size/30000 meters from goal")
        public void seconds0130000meters(){
            Lander result = xAcceleration(0.1, 30000, -90);
            assertEquals(0, result.getPosition().getX(), accuracy);
            assertEquals(0, result.getVelocity().getX(), accuracy);
        }
        @Test
        @DisplayName("0.1 seconds step size/-30000 meters from goal")
        public void seconds01Minus30000meters(){
            Lander result = xAcceleration(0.1, -30000, 90);
            assertEquals(0, result.getPosition().getX(), accuracy);
            assertEquals(0, result.getVelocity().getX(), accuracy);
        }


        private Lander xAcceleration(double stepSize, double distance, double angle){
            Lander lander = new Lander(new Vector2d(distance, 250000), new Vector2d(0, 0), 6000, 0, angle, 0);
            ArrayList<Lander> xAccelration = new Phase2().phase2(lander, 150, stepSize);
            return xAccelration.get(xAccelration.size() - 1);
        }
    }

    @Nested
    @DisplayName("Rotation by degree")
    class rotationThruster{

        @Test
        @DisplayName("from 0 to 90")
        public void from0to90(){
            Lander result = rotation(1, 30000, 0, 90);
            assertEquals(90, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }
        @Test
        @DisplayName("from 0 to 45")
        public void from0to45(){
            Lander result = rotation(1, -30000, 0, 45);
            assertEquals(45, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }

        @Test
        @DisplayName("from 90 to 0")
        public void from90to0(){
            Lander result = rotation(0.1, 30000, 90, 0);
            assertEquals(0, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }
        @Test
        @DisplayName("from 45 to 0")
        public void from45to0(){
            Lander result = rotation(0.1, -30000,  45, 0);
            assertEquals(0, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }

        @Test
        @DisplayName("from 0 to -90")
        public void from0toMinus90(){
            Lander result = rotation(1, 30000, 0, -90);
            assertEquals(-90, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }
        @Test
        @DisplayName("from 0 to -45")
        public void from0toMinus45(){
            Lander result = rotation(1, -30000, 0, -45);
            assertEquals(-45, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }

        @Test
        @DisplayName("from -90 to 0")
        public void fromMinus90to0(){
            Lander result = rotation(0.1, 30000, -90, 0);
            assertEquals(0, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }
        @Test
        @DisplayName("from -45 to 0")
        public void fromMinus45to0(){
            Lander result = rotation(0.1, -30000,  -45, 0);
            assertEquals(0, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }

        @Test
        @DisplayName("from -90 to 45")
        public void fromMinus90to45(){
            Lander result = rotation(0.1, 30000, -90, 45);
            assertEquals(45, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }
        @Test
        @DisplayName("from 45 to -90")
        public void from45toMinus90(){
            Lander result = rotation(0.1, -30000,  -45, -90);
            assertEquals(-90, result.getRotation(), accuracy);
            assertEquals(0, result.getRotationVelocity(), accuracy);
        }


        private Lander rotation(double stepSize, double distance, double angle, double theta){
            Lander lander = new Lander(new Vector2d(distance, 250000), new Vector2d(0, 0), 6000, 0, angle, 0);
            ArrayList<Lander> xAccelration = new RotationPhase().rotationPhase(lander, 150, stepSize, theta);
            return xAccelration.get(xAccelration.size() - 1);
        }
    }

    @Nested
    @DisplayName("Landing with Velocity Y = -735.0 meter per second")
    class yCoordinateThruster{

        @Test
        @DisplayName("Landing 50000 meters")
        public void landing50000(){
            Lander result = landing(50000);
            assertEquals(0, result.getPosition().getY(), accuracy);
            assertEquals(0, result.getVelocity().getY(), accuracy);
        }

        @Test
        @DisplayName("Landing 75000 meters")
        public void landing75000(){
            Lander result = landing(75000);
            assertEquals(0, result.getPosition().getY(), accuracy);
            assertEquals(0, result.getVelocity().getY(), accuracy);
        }
        @Test
        @DisplayName("Landing 100000 meters")
        public void landing100000(){
            Lander result = landing(1000000);
            assertEquals(0, result.getPosition().getY(), accuracy);
            assertEquals(0, result.getVelocity().getY(), accuracy);
        }

        @Test
        @DisplayName("Landing 120000 meters")
        public void landing30000(){
            Lander result = landing(120000);
            assertEquals(0, result.getPosition().getY(), accuracy);
            assertEquals(0, result.getVelocity().getY(), accuracy);
        }


        private Lander landing(double distance){
            Lander lander = new Lander(new Vector2d(0, distance), new Vector2d(0, -735.0), 6000, 0, 0, 0);
            ArrayList<Lander> xAccelration = new Phase4().phase4(lander, 0.1);
            return xAccelration.get(xAccelration.size() - 1);
        }
    }
}
