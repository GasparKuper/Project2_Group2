package MAIN;

import MAIN.GUI.Charts.LineChartSample;
import MAIN.GUI.SolarSystem.UI;
import MAIN.GUI.Solvers.SolversGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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



        //TEXT
        Text text = new Text("TITAN ODYSSEY");
        text.setFont(font);
        //Setting the color
        text.setFill(Color.ALICEBLUE);

        //Setting the Stroke
        text.setStrokeWidth(2);

        //Setting the stroke color
        text.setStroke(Color.BLACK);

        //BUTTON SOLAR SYSTEM
        Button buttonGUI = new Button("Solar System");
        buttonGUI.setFont(font);
        buttonGUI.setTextFill(Color.GRAY);
        buttonGUI.setOnAction(actionEvent ->  {
            stage.close();
            UI ui = new UI();
            try {
                ui.start(stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        //BUTTON LINE CHART
        Button buttonChart = new Button("Line Chart");
        buttonChart.setFont(font);
        buttonChart.setTextFill(Color.GRAY);
        buttonChart.setOnAction(actionEvent ->  {
            stage.close();
            LineChartSample lineChartSample = new LineChartSample();
            lineChartSample.start(stage);
        });

        //BUTTON SOLVERS
        Button buttonSolver = new Button("Solvers");
        buttonSolver.setFont(font);
        buttonSolver.setTextFill(Color.GRAY);
        buttonSolver.setOnAction(actionEvent ->  {
            stage.close();
            SolversGUI SolversGUI = new SolversGUI();
            SolversGUI.start(stage);
        });

        VBox buttons = new VBox(buttonGUI, buttonChart, buttonSolver);
        buttons.setSpacing(30);


        VBox vbox = new VBox(text, buttons);
        vbox.setSpacing(90);

        //Background
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/Image/Background.jpg", 800, 400, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        vbox.setBackground(background);

        //Settings
        vbox.getAlignment();

        Scene scene = new Scene(vbox, 800, 400);

        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
