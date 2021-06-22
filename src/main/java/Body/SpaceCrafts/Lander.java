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

    //Direction of the wind
    private static double DirectionWind = 1.0;

    public Vector2d generateRandomWind(double step, double deviation){
        double max = 1.0 + deviation;
        double min = 1.0 - deviation;
        //todo For experiments change the deviation
        double randomDeviation = ((Math.random() * (max - min)) + min);

        //Change direction of the wind 5%
        double randomNumber = Math.random();
        if(randomNumber > 0.75 && randomNumber < 0.8)
            DirectionWind *= -1;

        //Maximum speed of the wind
        //https://www.nasa.gov/mission_pages/cassini/whycassini/cassinif-20070601-05.html
        double maxSpeed = 120;

        //On the land of the titan, the wind force is 0.3 meters per second
        //Based on our assumptions, we can assume that 1 km from the Titan = 1 m/s, but its not realistic,
        // if the lander above 120 km, the speed of the wind is increased,
        // so on 120km, we have the maximum speed and above the wind force is decreased
        double heightInfluence = getPosition().getY() / 120000.0;

        if(heightInfluence >= 2.0)
            heightInfluence = 0;

        if(heightInfluence > 1.0)
            heightInfluence -= 1;

        //Area of the lander
        double sideArea = 25.0;

        double windSpeed = ((maxSpeed * heightInfluence) * randomDeviation) * DirectionWind;

        //Wind Force
        double windForce = sideArea * windSpeed;

        //Wind acceleration = Wind Force / mass of the lander
        double windAcceleration = windForce/mass;

        return new Vector2d(windAcceleration * step, 0);
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
