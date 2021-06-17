package WindModel;

import Body.Vector.Vector2d;

import java.awt.*;

public abstract class Object {

    protected ID id;
    protected Vector2d position;
    protected Vector2d velocity;
    protected double mass;
    protected double fuel;
    protected double rotation;
    protected double rotationVelocity;

    public Object(Vector2d position, Vector2d velocity, double fuel, double rotation, double rotationVelocity, ID id){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.fuel = fuel;
        this.rotation = rotation;
        this.rotationVelocity = rotationVelocity;
        this.id = id;
    }

    // constructor for the wind speed flag tester
    public Object(Vector2d position){
        this.position = position;
    }

    public abstract Rectangle getBounds();

    public abstract void tick();
    public abstract void render(Graphics2D g);


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

    public double getRotation() { return this.rotation; }

    public void setRotation(double rotation) { this.rotation = rotation; }

    public double getRotationVelocity() { return this.rotationVelocity; }

    public void setRotationVelocity(double rotationVelocity) { this.rotationVelocity = rotationVelocity; }

    public ID getId(){
        return this.id;
    }

    public void setXVelocity(double velocity) {
        this.velocity.setX(velocity);
    }

    public double getXVelocity() {
        return this.velocity.getX();
    }

    public void setYVelocity(double velocity) {
        this.velocity.setY(velocity);
    }

    public double getYVelocity() {
        return this.velocity.getY();
    }


}
