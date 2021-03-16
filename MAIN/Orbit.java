package MAIN;

import MAIN.Body.*;
import MAIN.Interfaces.*;
import javafx.scene.shape.Sphere;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.image.Image;

public class Orbit {

	public final Vector3dInterface solarSystemBarycenter = new Vector3d(1500/2.0, 0.0, -750/2.0); // position of the SSB on the screen

	private Vector3dInterface position; // position of the planet compared to the SSB
	private Sphere shape; // 3d component that models the orbit

	/**
	Creates an orbit based on its radius and its position compared to the SSB
	@param x distance in meters between the orbit and the SSB on the x-axis
	@param y distance in meters between the orbit and the SSB on the y-axis
	@param z distance in meters between the orbit and the SSB on the z-axis
	@param radius radius of its model in meters
	**/
	public Orbit(double x, double y, double z, double radius) {
		this.position = new Vector3d(x, y, z).add(solarSystemBarycenter);
		this.shape = new Sphere(radius);

		this.shape.translateXProperty().set(position.getX());
		this.shape.translateYProperty().set(-position.getZ());
		this.shape.translateZProperty().set(position.getY());
	}
	/**
	@return the component modelling the orbit
	**/
	public Sphere getShape() {
		return this.shape;
	}

	/**
	@return vector indicating the location of the orbit compared to the SSB
	**/
	public Vector3dInterface getPosition() {
		return this.position;
	}

	/**
	Sets the visual overlay of the orbit
	@param fileName name of the file used as overlay
	**/
	public void setImage(String fileName) {
		PhongMaterial overlay = new PhongMaterial();
		overlay.setDiffuseMap(new Image((fileName)));
		this.shape.setMaterial(overlay);
	}
	
	/**
	Sets a self-illuminating overlay for the orbit
	@param fileName name of the file used as overlay
	**/
	public void setLight(String fileName) {
		PhongMaterial overlay = new PhongMaterial();
		overlay.setSelfIlluminationMap(new Image(fileName));
		this.shape.setMaterial(overlay);
	}

}
