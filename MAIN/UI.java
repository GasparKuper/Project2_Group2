package MAIN;
// Scale x,y,z  - 1:1*10^(-9)
// Scale volume - 1:1*10^(-6)

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.image.*;

public class UI extends Application{

	public static final int WIDTH = 1500;
	public static final int HEIGHT = 750;

	public void start(Stage primaryStage) throws Exception {

		Group solarSystem = new Group();
		Scene scene = new Scene(solarSystem, WIDTH, HEIGHT);

		Orbit sun = new Orbit(-0.6806783239281648, 1.080005533878725, 0.006564012751690170, 695.508);
		sun.setImage("/Image/Textures/Sun.JPG");
		solarSystem.getChildren().add(sun.getShape());

		Orbit mercury = new Orbit(0.006047855986424127, -68.01800047868888, -5.702742359714534, 2.4397);
		mercury.setImage("/Image/Textures/Mercury.JPG");
		solarSystem.getChildren().add(mercury.getShape());

		Orbit venus = new Orbit(-94.35345478592035, 53.50359551033670, 6.131453014410347, 6.0518);
		venus.setImage("/Image/Textures/Venus.JPG");
		solarSystem.getChildren().add(venus.getShape());

		Orbit earth = new Orbit(-147.1922101663588, -28.60995816266412, 0.008278183193596080, 6.371);
		earth.setImage("/Image/Textures/Earth.JPG");
		solarSystem.getChildren().add(earth.getShape());

		Orbit moon = new Orbit(-147.2343904597218, -28.22578361503422, 0.01052790970065631, 1.7375);
		moon.setImage("/Image/Textures/Moon.JPG");
		solarSystem.getChildren().add(moon.getShape());

		Orbit mars = new Orbit(-36.15638921529161, -216.7633037046744, -3.687670305939779, 3.3895);
		mars.setImage("/Image/Textures/Mars.JPG");
		solarSystem.getChildren().add(mars.getShape());

		Orbit jupiter = new Orbit(178.1303138592153, -755.1118436250277, -0.8532838524802327, 69.911);
		jupiter.setImage("/Image/Textures/Jupiter.JPG");
		solarSystem.getChildren().add(jupiter.getShape());

		Orbit saturn = new Orbit(632.8646641500651, -1358.172804527507, -1.578520137930810, 58.232);
		saturn.setImage("/Image/Textures/Jupiter.JPG");
		solarSystem.getChildren().add(saturn.getShape());

		Orbit titan = new Orbit(633.2873118527889, -1357.175556995868, -2.134637041453660, 2.5755);
		titan.setImage("/Image/Textures/Titan.JPG");
		solarSystem.getChildren().add(titan.getShape());

		Orbit uranus = new Orbit(2395.195786685187, 1744.450959214586, -24.55116324031639, 25.362);
		uranus.setImage("/Image/Textures/Uranus.JPG");
		solarSystem.getChildren().add(uranus.getShape());

		Orbit neptune = new Orbit(4382.692942729203, -909.3501655486243, -82.27728929479486, 24.622);
		neptune.setImage("/Image/Textures/Neptune.JPG");
		solarSystem.getChildren().add(neptune.getShape());

		ImageView background = new ImageView("/Image/Textures/Space.JPG");
		background.setPreserveRatio(true);
		background.setFitWidth(WIDTH*1.3);
		background.setFitHeight(HEIGHT*1.2);
		solarSystem.getChildren().add(background);
		background.toBack();

		primaryStage.setTitle("Flight to Titan - Group 20");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}