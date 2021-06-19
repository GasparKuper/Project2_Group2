import Body.SpaceCrafts.Lander;
import Controller.OpenLoopController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class TrajectoryLanderTest {

    @Test
    @DisplayName("Test trajectory of the lander")
    public void testTrajectoryLander(){
        testOutput();
    }

    private void testOutput() {
        OpenLoopController lander = new OpenLoopController();
        ArrayList<Lander> trajectory = lander.land(lander.probeOnTheOrbitTitan());
        try {

            FileWriter writer = new FileWriter("trajectory_Lander.csv");
            String header = "seconds,x,y,velocity_x,velocity_y,angle_degree,angle_velocity";
            writer.write(header + "\n");
            BigDecimal a = new BigDecimal("0.1");
            for (int i = 0; i < trajectory.size(); i++) {
                String row = a + "," + trajectory.get(i).getPosition().getX() + "," + trajectory.get(i).getPosition().getY() + "," + trajectory.get(i).getVelocity().getX() + "," + trajectory.get(i).getVelocity().getY() + "," + trajectory.get(i).getRotation() + "," + trajectory.get(i).getRotationVelocity();
                writer.write(row + "\n");
                a = a.add(new BigDecimal("0.1"));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
