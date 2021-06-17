package GUI.SolarSystem;
// Scale x,y,z  - 1:1*10^(-9)
// Scale volume - 1:1*10^(-6)

import Body.Planets.Data;
import Body.Planets.PlanetBody;
import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Controller.OpenLoopController;
import Interfaces.ODESolverInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import ODESolver.ProbeSimulator;
import Run.CalculationForProbe.OrbitPlanet;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static Constant.Constant.*;


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

		Pane solarSystem = new Pane();
		solarSystem.setCache(true);
		solarSystem.setCacheHint(CacheHint.SPEED);

		Orbit sun = new Orbit((double) 69634 / 1000);
		sun.setLight("/Image/Textures/Sun.JPG");
		solarSystem.getChildren().add(sun.getShape());

		Orbit mercury = new Orbit( 24390.7 / 3000);
		mercury.setImage("/Image/Textures/Mercury.JPG");
		solarSystem.getChildren().add(mercury.getShape());

		Orbit venus = new Orbit( 60510.8 / 4000);
		venus.setImage("/Image/Textures/Venus.JPG");
		solarSystem.getChildren().add(venus.getShape());

		Orbit earth = new Orbit((double) 63710 / 3000);
		earth.setImage("/Image/Textures/Earth.JPG");
		solarSystem.getChildren().add(earth.getShape());

		Orbit moon = new Orbit(17380.1 / 3000);
		moon.setImage("/Image/Textures/Moon.JPG");
		solarSystem.getChildren().add(moon.getShape());

		Orbit mars = new Orbit( (double) 33962 / 2000);
		mars.setImage("/Image/Textures/Mars.JPG");
		solarSystem.getChildren().add(mars.getShape());

		Orbit jupiter = new Orbit((double) 71492 / 1500);
		jupiter.setImage("/Image/Textures/Jupiter.JPG");
		solarSystem.getChildren().add(jupiter.getShape());

		Orbit saturn = new Orbit( (double) 60268 / 1500);
		saturn.setImage("/Image/Textures/Saturn.JPG");
		solarSystem.getChildren().add(saturn.getShape());

		Orbit titan = new Orbit(25750.5 / 3000);
		titan.setImage("/Image/Textures/Titan.JPG");
		solarSystem.getChildren().add(titan.getShape());

		Orbit uranus = new Orbit((double) 25559 / 3000);
		uranus.setImage("/Image/Textures/Uranus.JPG");
		solarSystem.getChildren().add(uranus.getShape());

		Orbit neptune = new Orbit( (double) 24764 / 3000);
		neptune.setImage("/Image/Textures/Neptune.JPG");
		solarSystem.getChildren().add(neptune.getShape());

		Orbit probe = new Orbit((double) 63710 / 2000); //500 meters
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
		camera.translateXProperty().set(0); // setting the camera in the solar system
		camera.translateYProperty().set(0);  // setting the camera in the solar system
		camera.translateZProperty().set(-7000); // moving the camera back in the scene
		camera.setNearClip(1);
		camera.setFarClip(100000);

		Translate pivot = new Translate();


		camera.getTransforms().addAll(
				pivot,
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
					camera.translateZProperty().set(-3000);
				}
				case P -> {
					camera.translateXProperty().set(orbitArr[11].getShape().getTranslateX());
					camera.translateYProperty().set(orbitArr[11].getShape().getTranslateY());
					camera.translateZProperty().set(-3000);
				}
				case N -> {
					camera.translateXProperty().set(0);
					camera.translateYProperty().set(0);
					camera.translateZProperty().set(-7000);
				}
			}
		});

		//Background
		Group back = new Group();
		back.getChildren().add(solarSystem);
		back.getChildren().add(preImage());

		Scene scene = new Scene(back, 0, 0,true);
		scene.setFill(Color.BLACK);

		scene.setCamera(camera);
		primaryStage.setTitle("Flight to Titan - Group 10");
		primaryStage.setScene(scene);
		path();
		primaryStage.show();
		ptr.play();
	}

	private ParallelTransition ptr = new ParallelTransition();

	/**
	 * Calculate a path for every planet
	 */
	 private void path() {
	 	SOLVER = 3;

		 ProbeSimulator simulator = new ProbeSimulator();

		 simulator.trajectory(STARTPOS, VELOCITIES[SOLVER-1], FINALTIME[SOLVER-1], STEPSIZE);

		 State[] state = simulator.getTrajectory();

		 double day = 24*60*60;
		 double year = 30 * day;

		 State cloneState = state[state.length-1].clone();


		 Vector3d orbitVelocity = new OrbitPlanet().orbitSpeed((Vector3d) cloneState.position, cloneState.celestialBody.get(8).getPosition());

		 orbitVelocity = (Vector3d) orbitVelocity.add(cloneState.celestialBody.get(8).getVelocity());

		 cloneState.velocity = orbitVelocity;
		 cloneState.celestialBody.get(11).setVelocity(orbitVelocity);

		 ODESolver simulateODE = new ODESolver();

		 State[] state2 = (State[]) simulateODE.solve(new ODEFunction(), cloneState, year, 60);

		 State cloneState2 = state2[state2.length-1].clone();

		 Vector3d backToHome = new Vector3d(-19457.51190185663,8694.88542609827800331,1332.7261805534363);

		 cloneState2.velocity = backToHome;
		 cloneState2.celestialBody.get(11).setVelocity(backToHome);

		 State[] state3 = (State[]) simulateODE.solve(new ODEFunction(), cloneState2, 6.167E7-84, STEPSIZE);

		 int maxLength = state.length + state2.length + state3.length;
		 State[] result = new State[maxLength];

		 for (int i = 0; i < state.length; i++) {
			 result[i] = state[i];
		 }

		 int point = 0;
		 for (int i = state.length; i < state2.length + state.length; i++) {
			 result[i] = state2[point++];
		 }

		 point = 0;
		 for (int i = state2.length + state.length; i < maxLength; i++) {
			 result[i] = state3[point++];
		 }

		 OpenLoopController controller = new OpenLoopController();
		 controller.land(result);


	 //start animation
	 this.smoothUpdate(result);
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
					path.add((xS + 25.0*(x - xS)) / 550000000);
					path.add((yS + 25.0*(y - yS)) / 550000000);
				} else {
					path.add(x / 550000000);
					path.add(y / 550000000);
				}
			}


			//Insert a path and start animation
			polyline.getPoints().addAll(path);
			polyline.setStroke(Color.BLACK);

			PathTransition transition = new PathTransition();
			transition.setNode(f.getOrbit()[i].getShape());
			transition.setDuration(Duration.seconds(150));
			transition.setPath(polyline);
			transition.setCycleCount(PathTransition.INDEFINITE);

			ptr.getChildren().add(transition);
		}
	}

	private ImageView preImage(){
		Image image = new Image(getClass().getResourceAsStream("/Image/star.jpg"));
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.getTransforms().add(new Translate(-image.getWidth()/2, -image.getHeight()/2, 0));
		return imageView;
	}
}
