import Body.SpaceCrafts.Lander;
import Controller.CloseLoopController.CloseLoopController;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrajectoryLanderCloseLoopTest {

    private final double accuracy = 0.1;

    private final Lander result = new CloseLoopController().getLast();

    @Test
    @DisplayName("Test position")
    public void testTrajectoryLanderPosition(){
        double x = Math.abs(result.getPosition().getX());
        double y = Math.abs(result.getPosition().getY());
        assertTrue(x < accuracy);
        assertTrue(y < accuracy);
    }

    @Test
    @DisplayName("Test velocity")
    public void testTrajectoryLanderVelocity(){
        double x = Math.abs(result.getVelocity().getX());
        double y = Math.abs(result.getVelocity().getY());
        assertTrue(x < accuracy);
        assertTrue(y < accuracy);
    }

    @Test
    @DisplayName("Test rotation")
    public void testTrajectoryLanderRotation(){
        double x = Math.abs(result.getRotation());
        double y = Math.abs(result.getRotationVelocity());
        assertTrue(x < accuracy);
        assertTrue(y < accuracy);
    }
}
