package SolarSystem;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Orbit {

	private Sphere shape; // 3d component that models the orbit

	/**
	Creates an orbit based on its radius and its position compared to the SSB
	@param radius radius of its model in meters
	**/
	public Orbit(double radius) {
		this.shape = new Sphere(radius);
	}
	/**
	@return the component modelling the orbit
	**/
	public Sphere getShape() {
		return this.shape;
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
