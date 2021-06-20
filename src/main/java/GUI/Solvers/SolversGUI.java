package GUI.Solvers;

import Body.SpaceCrafts.Lander;
import Controller.CloseLoopController.CloseLoopController;
import Controller.OpenLoopController.OpenLoopController;
import Run.CalculationOutput;
import GUI.RunGui.Run;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Constant.Constant.SOLVER;


/**
 * Run solvers with the program parameters
 */
public class SolversGUI extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Solvers");
        stage.setResizable(false);

        //Icon
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Image/Logo.jpg")));


        MenuBar menuBar = new MenuBar();

        //Exit
        Menu exit = new Menu("EXIT");
        MenuItem exit1 = new MenuItem("EXIT");
        exit1.setOnAction(e -> {
            Run run = new Run();
            try {
                stage.close();
                run.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        exit.getItems().add(exit1);

        //Solvers
        Menu solver = new Menu("Solvers");
        MenuItem eulerSymplectic_solver = new MenuItem("Symplectic Euler");
        MenuItem eulerImplicit_solver = new MenuItem("Implicit Euler");
        MenuItem verletVelocity_solver = new MenuItem("Velocity-Verlet");
        MenuItem runge_solver = new MenuItem("4th-Runge-Kutta");


        //Symplectic Euler
        eulerSymplectic_solver.setOnAction(e -> {
            SOLVER = 1;
            new CalculationOutput().Solver();
        });

        //Implicit Euler
        eulerImplicit_solver.setOnAction(e -> {
            SOLVER = 2;
            new CalculationOutput().Solver();
        });

        //Velocity-Verlet
        verletVelocity_solver.setOnAction(e -> {
            SOLVER = 3;
            new CalculationOutput().Solver();
        });

        //4th-Runge-Kutta
        runge_solver.setOnAction(e -> {
            SOLVER = 4;
            new CalculationOutput().Solver();
        });

        //Landing
        Menu landing = new Menu("Landing");
        MenuItem closeLanding = new MenuItem("Close-loop");
        MenuItem openLanding = new MenuItem("Open-loop");

        closeLanding.setOnAction(e -> {
            ArrayList<Lander> lander = new CloseLoopController().land();
            printResult(lander);
        });

        openLanding.setOnAction(e -> {
            ArrayList<Lander> lander = new OpenLoopController().land();
            printResult(lander);
        });

        landing.getItems().addAll(closeLanding, openLanding);

        solver.getItems().addAll(eulerSymplectic_solver, eulerImplicit_solver,
                verletVelocity_solver, runge_solver);

        menuBar.getMenus().addAll(solver, landing, exit);

        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 400, 600);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Print the result of the phase
     * @param phase result with the data of the phase
     */
    private void printResult(ArrayList<Lander> phase){
            Lander t = phase.get(phase.size() - 1);
            System.out.println("Position = " + t.getPosition().getX() + "     " + t.getPosition().getY());
            System.out.println("Velocity = " + t.getVelocity().getX() + "     " + t.getVelocity().getY());
            System.out.println("Degree = " + t.getRotation());
            System.out.println("Degree velocity = " + t.getRotationVelocity());
    }
}
