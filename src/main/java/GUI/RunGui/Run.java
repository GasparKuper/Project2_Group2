package GUI.RunGui;

import GUI.Charts.LineChartSample;
import GUI.SolarSystem.UI;
import GUI.Solvers.SolversGUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

public class Run extends Application {

    @Override
    public void start(Stage stage) {
        //Icon
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Image/Logo.jpg")));

        //FONT
        Font font = Font.font("Courier New", FontWeight.BOLD, 36);

        //TITLE
        stage.setTitle("Titan Odyssey");

        //Resolution
        stage.setResizable(false);

        Pane root = new Pane();

        //BUTTON SOLAR SYSTEM
        Button buttonGUI = new Button("Solar System");
        buttonGUI.setFont(font);
        buttonGUI.setTextFill(Color.BLACK);

        buttonGUI.setOnAction(actionEvent ->  {
            stage.close();
            UI ui = new UI();
            try {
                ui.start(stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        buttonGUI.setLayoutX(450);
        buttonGUI.setLayoutY(150);


        //BUTTON LINE CHART
        Button buttonChart = new Button("Line Chart");
        buttonChart.setFont(font);
        buttonChart.setTextFill(Color.BLACK);
        buttonChart.setOnAction(actionEvent ->  {
            stage.close();
            LineChartSample lineChartSample = new LineChartSample();
            lineChartSample.start(stage);
        });
        buttonChart.setLayoutX(450);
        buttonChart.setLayoutY(250);


        //BUTTON SOLVERS
        Button buttonSolver = new Button("Solvers");
        buttonSolver.setFont(font);
        buttonSolver.setTextFill(Color.BLACK);
        buttonSolver.setOnAction(actionEvent ->  {
            stage.close();
            SolversGUI SolversGUI = new SolversGUI();
            SolversGUI.start(stage);
        });
        buttonSolver.setLayoutX(450);
        buttonSolver.setLayoutY(350);

        // Adding the buttons to the root node
        root.getChildren().add(buttonSolver);
        root.getChildren().add(buttonChart);
        root.getChildren().add(buttonGUI);

        //Background
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/Image/BG_StartPage.png", 800, 500, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        root.setBackground(background);

        Scene scene = new Scene(root, 800, 500);

        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
