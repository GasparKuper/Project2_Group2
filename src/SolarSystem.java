package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;


/* Program has a sun and an earth.
*  On line 88/89 you can choose between a front and top camera.
*  The earth orbits the sun, but for some reason does not go invisible, when it is behind the sun.
*
* */


public class SolarSystem extends Application {

    private static final int WIDTH = 1400; // Width of the scene
    private static final int HEIGHT = 800; // Height of the scene
    private static final int SIZE_SUN = 100; // Size of the sun
    private static final int SIZE_EARTH = 50; // Size of the sun
    Sphere earth = new Sphere(SIZE_EARTH);


    public static final Point3D Y_AXIS = new Point3D(0,1,0); // Rotate around X_AXIS
    public static final Point3D X_AXIS = new Point3D(1,0,0); // Rotate around X_AXIS
    public static final Point3D Z_AXIS = new Point3D(0,1,0); // Rotate around X_AXIS

    @Override
    public void start(Stage primaryStage) {

        // Creating the sun sphere
        Sphere sun = new Sphere(SIZE_SUN);
        PhongMaterial sunMaterial = new PhongMaterial(); // Creating the sun Material
        sunMaterial.setDiffuseColor(Color.YELLOW); // Setting the Material color
        sunMaterial.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("/Resources/Map_of_the_full_sun.jpg")));
        sun.setMaterial(sunMaterial); // Setting the material to the sun


        // Creating the earth sphere
        //Sphere earth = new Sphere(SIZE_EARTH);
        PhongMaterial earthMaterial = new PhongMaterial(); // Creating the earth Material
        earthMaterial.setDiffuseColor(Color.LIGHTSKYBLUE); // Setting the Material color
        earthMaterial.setDiffuseMap(new Image(getClass().getResourceAsStream("/Resources/earthmap-1280x720.jpg")));
        earth.setMaterial(earthMaterial); // Setting the material to the earth

        earth.setRotationAxis(Rotate.Y_AXIS); // setting the rotation Axis of the earth
        earth.getTransforms().addAll(new Translate(200,0,200));

      // Container to contain multiple items
        Group planets = new Group();

        planets.getChildren().add(sun); // Adding the sun to the planet group
        planets.getChildren().add(earth); // Adding the earth to the planet group

        // Camera for a Front view of the universe
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(25); // setting the camera to be telephoto
        camera.translateXProperty().set(0); // setting the camera in the solar system
        camera.translateYProperty().set(-250);  // setting the camera in the solar system
        camera.translateZProperty().set(-1000); // moving the camera back in the scence
        camera.setRotationAxis(X_AXIS);
        camera.setRotate(-15);
        camera.setNearClip(1);
        camera.setFarClip(100000);

        // Camera for a top view of the universe
        PerspectiveCamera cameraTop = new PerspectiveCamera(true);
        cameraTop.translateXProperty().set(0); // setting the camera in the solar system
        cameraTop.translateYProperty().set(1000);  // setting the camera in the solar system
        cameraTop.translateZProperty().set(0); // moving the camera back in the scence
        cameraTop.setRotationAxis(X_AXIS);
        cameraTop.setRotate(90);
        cameraTop.setNearClip(1);
        cameraTop.setFarClip(100000);

        // Creating the Scene
        Scene scene = new Scene(planets, WIDTH, HEIGHT);
        scene.setFill(Color.MIDNIGHTBLUE); // Creating the background color
        //scene.setCamera(cameraTop); // Setting the camera for a top view
        scene.setCamera(camera); // Setting the camera into the scene for a front view

        camera.translateXProperty().set(0); // setting the camera in the solar system
        camera.translateYProperty().set(-250);  // setting the camera in the solar system
        camera.translateZProperty().set(-1000); // moving the camera back in the scence
        camera.setRotationAxis(X_AXIS);
        camera.setRotate(-15);

        camera.setNearClip(1);
        camera.setFarClip(100000);

        // Rotation of the camera that does not work :(
        /*Transform transform1 = new Rotate(10, Rotate.X_AXIS);
        Transform transform2 = new Rotate(-10, Rotate.X_AXIS);*/


        // Zooming in and out of the scene with the W and S key
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    camera.translateZProperty().set(camera.getTranslateZ() + 100);
                    break;
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 100);
                    break;
                case E:
                    cameraTop.translateYProperty().set(cameraTop.getTranslateY() + 100);
                    break;
                case D:
                    cameraTop.translateYProperty().set(cameraTop.getTranslateY() - 100);
                    break;
            }
        });

        primaryStage.setTitle("Solar System"); // Creating the window
        primaryStage.setScene(scene);  // Adding the scene to the window
        primaryStage.show();  // making the window visible

        // Creation a timer that is called for each frame
        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                earth.setRotate(earth.getRotate() + 1); // With each frame the rotation will increase +1
            }
        };
        timer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
