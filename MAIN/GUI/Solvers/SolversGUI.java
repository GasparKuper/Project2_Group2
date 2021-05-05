package MAIN.GUI.Solvers;

import MAIN.CalculationOutput;
import MAIN.Run;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static MAIN.Constant.Constant.SOLVER;

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

        Menu solver = new Menu("Solvers");

        MenuItem euler_solver = new MenuItem("Euler solver");
        MenuItem verlet_solver = new MenuItem("Verlet solver");
        MenuItem runge_solver = new MenuItem("Runge-Kutta solver");

        CalculationOutput start = new CalculationOutput();

        euler_solver.setOnAction(e -> {
            SOLVER = 1;
            start.Solver();
        });

        verlet_solver.setOnAction(e -> {
            SOLVER = 2;
            start.Solver();
        });

        runge_solver.setOnAction(e -> {
            SOLVER = 3;
            start.Solver();
        });

        solver.getItems().add(euler_solver);
        solver.getItems().add(verlet_solver);
        solver.getItems().add(runge_solver);

        menuBar.getMenus().add(solver);
        menuBar.getMenus().add(exit);

        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 400, 600);

        stage.setScene(scene);
        stage.show();

    }
}
