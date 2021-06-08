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


}
