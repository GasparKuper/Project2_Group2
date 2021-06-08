package GUI.SolarSystem;
// Scale x,y,z  - 1:1*10^(-9)
// Scale volume - 1:1*10^(-6)

import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import static Constant.Constant.SOLVER;
import static Constant.Constant.THRUST;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Representation of the solar system in the GUI
 */
public class UI extends Application{

	//Array of sphere for the GUI
	private static Orbit[] orbitArr = new Orbit[12];

	/**
	 * Gets array of spheres for the GUI
	 * @return Array of spheres
	 */
	public Orbit[] getOrbit(){
		return orbitArr;
	}

	public void start(Stage primaryStage) throws FileNotFoundException {


		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Solar System");

		//Icon
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Image/Logo.jpg")));

		Group solarSystem = new Group();
		solarSystem.setCache(true);
		solarSystem.setCacheHint(CacheHint.SPEED);

		Scene scene = new Scene(solarSystem,800,500,true);
		scene.setFill(Color.BLACK);



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

		Orbit probe = new Orbit((double) 63710 / 6000); //500 meters
		probe.setImage("/Image/Textures/probe.JPG");
		solarSystem.getChildren().add(probe.getShape());


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



		PerspectiveCamera camera = new PerspectiveCamera(true);
		camera.setFieldOfView(25); // setting the camera to be telephoto
		camera.translateXProperty().set(0); // setting the camera in the solar system
		camera.translateYProperty().set(0);  // setting the camera in the solar system
		camera.translateZProperty().set(-7000); // moving the camera back in the scene
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
			switch (event.getCode()) {
				case V -> yRotate.angleProperty().set(yRotate.getAngle() + 10);

				case B -> yRotate.setAngle(yRotate.getAngle() - 10);

				case F -> {
					camera.translateYProperty().set(camera.getTranslateY() + 500);
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
		path();
	}

	/**
	 * Calculate a path for every planet
	 */
	 private void path() {
	 THRUST = true;
	 SOLVER = 3;
	 Vector3dInterface probe_relative_position = new Vector3d(4301000.0,-4692000.0,-276000.0);
	 Vector3dInterface probe_relative_velocity = new Vector3d(0, 0, 0); // 12.0 months
	 //Change parameters
	 double day = 246060;
	 double year = 365.25*day;
	 double ten_minutes = 60 * 10;

	 ProbeSimulator simulator = new ProbeSimulator();
	 simulator.trajectory(probe_relative_position, probe_relative_velocity, 6.167E7, ten_minutes);

	 State[] trajectoryOfAll = simulator.getTrajectory();

	 //start animation
	 this.smoothUpdate(trajectoryOfAll);
	 }

	/**
	 * Create a path for the planet, also scale coordinates
	 * @param state Trajectories of the planets and the probe
	 */
	private void smoothUpdate(State[] state){
		UI f = new UI();
		for(int i =0;i<f.getOrbit().length;i++) {
			f.getOrbit()[i].getShape().translateZProperty().set(0);
		}

		ParallelTransition ptr = new ParallelTransition();
		//Create a path for the planet (also here we scale coordinates)
		for (int i = 0; i < state[0].celestialBody.size(); i++) {
			Polyline polyline = new Polyline();
			ArrayList<Double> path = new ArrayList<>();
			for (int j = 0; j < state.length; j++) {
				double y = (state[j].celestialBody.get(0).getPosition().getX() - state[j].celestialBody.get(i).getPosition().getX());
				double x = (state[j].celestialBody.get(0).getPosition().getY() - state[j].celestialBody.get(i).getPosition().getY());
				if(i == 4 || i == 8) {
					double yS = (state[j].celestialBody.get(0).getPosition().getX() - state[j].celestialBody.get(i-1).getPosition().getX());
					double xS = (state[j].celestialBody.get(0).getPosition().getY() - state[j].celestialBody.get(i-1).getPosition().getY());
					path.add((xS + 25.0*(x - xS)) / 600000000);
					path.add((yS + 25.0*(y - yS)) / 600000000);
				} else {
					path.add(x / 600000000);
					path.add(y / 600000000);
				}
			}

			//Insert a path and start animation
			polyline.getPoints().addAll(path);
			PathTransition transition = new PathTransition();
			transition.setNode(f.getOrbit()[i].getShape());
			transition.setDuration(Duration.seconds(60));
			transition.setPath(polyline);
			transition.setCycleCount(PathTransition.INDEFINITE);
			ptr.getChildren().add(transition);
		}
		ptr.play();
	}
}
