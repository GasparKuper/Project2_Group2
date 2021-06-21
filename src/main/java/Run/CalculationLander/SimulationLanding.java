package Run.CalculationLander;

import Body.SpaceCrafts.Lander;
import Body.Vector.Vector2d;
import Controller.CloseLoopController.CloseLoopController;
import Controller.OpenLoopController.OpenLoopController;

import java.util.ArrayList;

public class SimulationLanding {

    public void template(){
        //todo Change only positions
        Lander lander = new Lander(new Vector2d(0, 180000), new Vector2d(0, 0), 6000, 0, 0, 0);

        //Open
        OpenLoopController open = new OpenLoopController();
        ArrayList<Lander> landerOpen = open.calculatePhase(lander, 20);
        printResult(landerOpen);


        //Close
        CloseLoopController close = new CloseLoopController();
        ArrayList<Lander> landerClose = close.calculatePhase(lander, 20);
        printResult(landerClose);
    }

    /**
     * Print the result of the phase
     * @param phase result with the data of the phase
     */
    private void printResult(ArrayList<Lander> phase){
        Lander first = phase.get(0);
        System.out.println("Initial position of the lander " + first.getPosition().getX() + "     " + first.getPosition().getY());
        Lander t = phase.get(phase.size() - 1);
        System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
        System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
        System.out.println("Degree = " + t.getRotation());
        System.out.println("Degree velocity = " + t.getRotationVelocity());
    }

    public static void main(String[] args) {
        new SimulationLanding().template();
    }
}
