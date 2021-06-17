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

    //Rotation
    private double rotation;

    //Rotation velocity
    private double rotationVelocity;

    public Lander(Vector2d position, Vector2d velocity, double mass, double fuel, double rotation, double rotationVelocity){
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.fuel = fuel;
        this.rotation = rotation;
        this.rotationVelocity = rotationVelocity;
    }

    public Lander(double mass, double fuel){
        this.mass = mass;
        this.fuel = fuel;
    }

    public Vector2d generateRandomWind(){

        int MAX_DEVIATION_WIND = 12;
        int MIN_DEVIATION_WIND = 8;

        // creating a random number between the max and min Deviation constants
        int random_int = (int)Math.floor(Math.random()*(MAX_DEVIATION_WIND - MIN_DEVIATION_WIND +1)+ MIN_DEVIATION_WIND);

        double randomDeviation = random_int * 0.01;

        // once the lander is below 300 km the wind changes the direction ! Wow !
        if (this.position.getY() < 300){
            randomDeviation = randomDeviation * -1;
        }

        // implementing the function of wind strength according to height
        double yScalar = 0.004 * position.getY() * position.getY() + 0.173 * position.getY();
        yScalar = yScalar * 0.0001;

        // adding the yScalar to the randomDeviation
        randomDeviation = randomDeviation + (randomDeviation * yScalar);

        Vector2d randomWindVector = new Vector2d(randomDeviation, 0);

        return randomWindVector;
    }

    public Lander clone(){
        return new Lander(position, velocity, mass, fuel, rotation, rotationVelocity);
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

    public double getRotation() { return this.rotation; }

    public void setRotation(double rotation) { this.rotation = rotation; }

    public double getRotationVelocity() { return this.rotationVelocity; }

    public void setRotationVelocity(double rotationVelocity) { this.rotationVelocity = rotationVelocity; }
}
