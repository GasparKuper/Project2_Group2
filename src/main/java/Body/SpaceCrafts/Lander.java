package Body.SpaceCrafts;

import Body.Vector.Vector2d;

public class Lander {

    //Position
    private Vector2d position;

    //Velocity
    private Vector2d velocity;

    //Mass without fuel
    private double mass;

    //Mass of fuel
    private double fuel;

    public Lander(Vector2d position, Vector2d velocity, double mass, double fuel){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.fuel = fuel;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
