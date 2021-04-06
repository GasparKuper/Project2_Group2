package MAIN;
// Scale x,y,z  - 1:1*10^(-9)
// Scale volume - 1:1*10^(-6)

import MAIN.GUI.Orbit;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
	public void start(Stage primaryStage){
		Group solarSystem = new Group();
		Scene scene = new Scene(solarSystem, WIDTH, HEIGHT,true);

		Orbit sun = new Orbit((double) 69634 / 1000);
		sun.setLight("/Image/Textures/Sun.JPG");
		solarSystem.getChildren().add(sun.getShape());

		Orbit mercury = new Orbit( 24390.7 / 6000);
		mercury.setImage("/Image/Textures/Mercury.JPG");
		solarSystem.getChildren().add(mercury.getShape());


		Orbit venus = new Orbit( 60510.8 / 6000);
		venus.setImage("/Image/Textures/Venus.JPG");
		solarSystem.getChildren().add(venus.getShape());

		Orbit earth = new Orbit((double) 63710 / 6000);
		earth.setImage("/Image/Textures/Earth.JPG");
		solarSystem.getChildren().add(earth.getShape());

		Orbit moon = new Orbit(17380.1 / 6000);
		moon.setImage("/Image/Textures/Moon.JPG");
		solarSystem.getChildren().add(moon.getShape());

		Orbit mars = new Orbit( (double) 33962 / 5000);
		mars.setImage("/Image/Textures/Mars.JPG");
		solarSystem.getChildren().add(mars.getShape());

		Orbit jupiter = new Orbit((double) 71492 / 6000);
		jupiter.setImage("/Image/Textures/Jupiter.JPG");
		solarSystem.getChildren().add(jupiter.getShape());

		Orbit saturn = new Orbit( (double) 60268 / 6000);
		saturn.setImage("/Image/Textures/Saturn.JPG");
		solarSystem.getChildren().add(saturn.getShape());

		Orbit titan = new Orbit(25750.5 / 6000);
		titan.setImage("/Image/Textures/Titan.JPG");
		solarSystem.getChildren().add(titan.getShape());

		Orbit uranus = new Orbit((double) 25559 / 6000);
		uranus.setImage("/Image/Textures/Uranus.JPG");
		solarSystem.getChildren().add(uranus.getShape());

		Orbit neptune = new Orbit( (double) 24764 / 6000);
		neptune.setImage("/Image/Textures/Neptune.JPG");
		solarSystem.getChildren().add(neptune.getShape());

		Orbit probe = new Orbit((double) 3000 / 6000); //500 meters
		probe.setImage("/Image/Textures/probe.JPG");
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
		//Changed
		Thread animation = new CalculationOutput();
		animation.start();

		PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.setFieldOfView(25); // setting the camera to be telephoto
		camera.translateXProperty().set(0); // setting the camera in the solar system
		camera.translateYProperty().set(0);  // setting the camera in the solar system
		camera.translateZProperty().set(-7000); // moving the camera back in the scence
		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(0);
		camera.setNearClip(1);
		camera.setFarClip(100000);

		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS); // Lets the camera rotate around the Y Axis

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
					camera.translateYProperty().set(camera.getTranslateY() + 500);
					System.out.println("X Position: " + camera.getTranslateX() + "  Y Position: " + camera.getTranslateY() + "  Z Position: " + camera.getTranslateZ());
				}
				case R -> {
					camera.translateYProperty().set(camera.getTranslateY() - 500);
				}
				case A -> {
					camera.translateXProperty().set(camera.getTranslateX() - 500);
				}
				case D -> {
					camera.translateXProperty().set(camera.getTranslateX() + 500);
				}
				case W -> {
					camera.translateZProperty().set(camera.getTranslateZ() + 500);
				}
				case S -> {
					camera.translateZProperty().set(camera.getTranslateZ() - 500);
				}
				case E -> {
					camera.translateXProperty().set(orbitArr[3].getShape().getTranslateX());
					camera.translateYProperty().set(orbitArr[3].getShape().getTranslateY());
					camera.translateZProperty().set(-500);
				}
				case T -> {
					camera.translateXProperty().set(orbitArr[7].getShape().getTranslateX());
					camera.translateYProperty().set(orbitArr[7].getShape().getTranslateY());
					camera.translateZProperty().set(-500);
				}
				case P -> {
					camera.translateXProperty().set(orbitArr[11].getShape().getTranslateX());
					camera.translateYProperty().set(orbitArr[11].getShape().getTranslateY());
					camera.translateZProperty().set(0);
				}
				case N -> {
					camera.translateXProperty().set(0);
					camera.translateYProperty().set(0);
					camera.translateZProperty().set(-7000);
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
