package MAIN;
// Scale x,y,z  - 1:1*10^(-9)
// Scale volume - 1:1*10^(-6)

import MAIN.Interfaces.Vector3dInterface;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class UI extends Application{

	public static final int WIDTH = 1500;
	public static final int HEIGHT = 750;
	public static Orbit[] orbitArr = new Orbit[12];
	public UI(){

	}
	public Orbit[] getOrbit(){
		return orbitArr;
	}
	public void start(Stage primaryStage) throws Exception {
		Initialize planets = new Initialize();
		Vector3dInterface[][] init = planets.getPlanets();
		Group solarSystem = new Group();
		Scene scene = new Scene(solarSystem, WIDTH, HEIGHT,true);


		Sphere sunPivot = new Sphere(50);
		sunPivot.setTranslateX(-0.6806783239281648);
		sunPivot.setTranslateY(-0.006564012751690170);
		sunPivot.setTranslateZ(-1.080005533878725);


		Orbit sun = new Orbit(-0.6806783239281648, 1.080005533878725, 0.006564012751690170, 695.508);
		sun.setLight("/Image/Textures/Sun.JPG");
		sun.setState(init[0][0],init[0][1]);
		solarSystem.getChildren().add(sun.getShape());

		Orbit mercury = new Orbit(0.006047855986424127, -68.01800047868888, -5.702742359714534, 2.4397);
		mercury.setImage("/Image/Textures/Mercury.JPG");
		mercury.setState(init[1][0],init[1][1]);
		solarSystem.getChildren().add(mercury.getShape());


		Orbit venus = new Orbit(-94.35345478592035, 53.50359551033670, 6.131453014410347, 6.0518);
		venus.setImage("/Image/Textures/Venus.JPG");
		venus.setState(init[2][0],init[2][1]);
		solarSystem.getChildren().add(venus.getShape());

		Orbit earth = new Orbit(-147.1922101663588, -28.60995816266412, 0.008278183193596080, 6.371);
		earth.setImage("/Image/Textures/Earth.JPG");
		earth.setState(init[3][0],init[3][1]);
		solarSystem.getChildren().add(earth.getShape());

		Orbit moon = new Orbit(-147.2343904597218, -28.22578361503422, 0.01052790970065631, 1.7375);
		moon.setImage("/Image/Textures/Moon.JPG");
		moon.setState(init[4][0],init[4][1]);
		solarSystem.getChildren().add(moon.getShape());

		Orbit mars = new Orbit(-36.15638921529161, -216.7633037046744, -3.687670305939779, 3.3895);
		mars.setImage("/Image/Textures/Mars.JPG");
		mars.setState(init[5][0],init[5][1]);
		solarSystem.getChildren().add(mars.getShape());

		Orbit jupiter = new Orbit(178.1303138592153, -755.1118436250277, -0.8532838524802327, 69.911);
		jupiter.setImage("/Image/Textures/Jupiter.JPG");
		jupiter.setState(init[6][0],init[6][1]);
		solarSystem.getChildren().add(jupiter.getShape());

		Orbit saturn = new Orbit(632.8646641500651, -1358.172804527507, -1.578520137930810, 58.232);
		saturn.setImage("/Image/Textures/Saturn.JPG");
		saturn.setState(init[7][0],init[7][1]);
		solarSystem.getChildren().add(saturn.getShape());

		Orbit titan = new Orbit(633.2873118527889, -1357.175556995868, -2.134637041453660, 2.5755);
		titan.setImage("/Image/Textures/Titan.JPG");
		titan.setState(init[8][0],init[8][1]);
		solarSystem.getChildren().add(titan.getShape());

		Orbit uranus = new Orbit(2395.195786685187, 1744.450959214586, -24.55116324031639, 25.362);
		uranus.setImage("/Image/Textures/Uranus.JPG");
		uranus.setState(init[9][0],init[9][1]);
		solarSystem.getChildren().add(uranus.getShape());

		Orbit neptune = new Orbit(4382.692942729203, -909.3501655486243, -82.27728929479486, 24.622);
		neptune.setImage("/Image/Textures/Neptune.JPG");
		neptune.setState(init[10][0],init[10][1]);
		solarSystem.getChildren().add(neptune.getShape());

		Orbit probe = new Orbit(-1.4718861838613153E2, -2.8615219147677864 ,8174296.311571818E-9, 24.622);
		probe.setImage("/Image/Textures/probe.JPG");
		probe.setState(init[11][0],init[11][1]);
		solarSystem.getChildren().add(probe.getShape());

		/*ImageView background = new ImageView("/Image/Textures/Space.JPG");
		background.setPreserveRatio(true);
		background.setFitWidth(WIDTH*1.3);
		background.setFitHeight(HEIGHT*1.2);
		solarSystem.getChildren().add(background);*/
		orbitArr[0] = sun;
		orbitArr[1] = mercury;
		orbitArr[2] = venus;
		orbitArr[3] = earth;
		orbitArr[4] = moon;
		orbitArr[5] = mars;
		orbitArr[6] = jupiter;
		orbitArr[7] = saturn;
		orbitArr[8] = titan;
		orbitArr[9] = uranus;
		orbitArr[10] = neptune;
		orbitArr[11] = probe;

		scene.setFill(Color.BLACK);
		
		//PointLight lighting = new PointLight();
		//solarSystem.getChildren().add(lighting);
		Thread animation = new Calculation(sun, mercury, venus, earth, moon, mars, jupiter, saturn, titan, uranus, neptune);
		animation.start();

		PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.setFieldOfView(25); // setting the camera to be telephoto
		camera.translateXProperty().set(-300); // setting the camera in the solar system
		camera.translateYProperty().set(0);  // setting the camera in the solar system
		camera.translateZProperty().set(-3300); // moving the camera back in the scence
		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(0);
		camera.setNearClip(1);
		camera.setFarClip(100000);

		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS); // Lets the camera rotate around the Y Axis
		yRotate.pivotXProperty().bind(sunPivot.translateXProperty());
		yRotate.pivotZProperty().bind(sunPivot.translateZProperty());
		yRotate.pivotYProperty().bind(sunPivot.translateYProperty());

		Rotate cameraRotate = new Rotate(0, Rotate.Y_AXIS);
		Translate pivot = new Translate();


		camera.getTransforms().addAll(
				pivot,
				yRotate,
				cameraRotate,
				new Translate(0,0,-500)
		);


		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
				case V:
					yRotate.angleProperty().set(yRotate.getAngle() + 10);
					//cameraRotate.angleProperty().set(cameraRotate.getAngle() -10);
					break;
			}

			switch (event.getCode()) {
				case B:
					yRotate.setAngle(yRotate.getAngle() - 10);
					//cameraRotate.angleProperty().set(cameraRotate.getAngle() + 10);
					break;
			}
		});


		// set the pivot for the camera position animation base upon mouse clicks on objects
		solarSystem.getChildren().stream()
				.filter(node -> !(node instanceof Camera))
				.forEach(node ->
						node.setOnMouseClicked(event -> {
							pivot.setX(node.getTranslateX());
							pivot.setY(node.getTranslateY());
							pivot.setZ(node.getTranslateZ());
						})
				);


		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			//				case Q:
			//					yRotate.angleProperty().set(yRotate.getAngle() + 10);
			//					System.out.println("yRotate" + yRotate.getAngle());
			//					break;
			switch (event.getCode()) {
				case F -> {
					camera.translateYProperty().set(camera.getTranslateY() + 100);
					System.out.println("X Position: " + camera.getTranslateX() + "  Y Position: " + camera.getTranslateY() + "  Z Position: " + camera.getTranslateZ());
				}
				case R -> {
					camera.translateYProperty().set(camera.getTranslateY() - 100);
				}
				case A -> {
					camera.translateXProperty().set(camera.getTranslateX() - 100);
				}
				case D -> {
					camera.translateXProperty().set(camera.getTranslateX() + 100);
				}
				case W -> {
					camera.translateZProperty().set(camera.getTranslateZ() + 100);
				}
				case S -> {
					camera.translateZProperty().set(camera.getTranslateZ() - 100);
				}
			}
		});


		scene.setCamera(camera);  // Setting the camera into the scene
		primaryStage.setTitle("Flight to Titan - Group 10");
		primaryStage.setScene(scene);
		primaryStage.show();
	}



	public static void main(String args[]) {
		launch(args);
	}

}
