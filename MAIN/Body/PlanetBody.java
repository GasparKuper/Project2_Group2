package MAIN.Body;

import MAIN.Interfaces.Vector3dInterface;

public class PlanetBody {

    //Mass
    private double m;

    //Position
    public Vector3d position;

    //Velocity
    public Vector3d velocity;

    /**
     * Constructor of the planet body
     * @param m Mass
     * @param position Position
     * @param velocity Velocity
     */
    public PlanetBody(double m, Vector3dInterface position, Vector3dInterface velocity){
        this.m = m;
        this.position = (Vector3d) position;
        this.velocity = (Vector3d) velocity;
    }

    /**
     * Gets Mass of the planet
     * @return Mass
     */
    public double getM() {
        return m;
    }

    /**
     * Gets Position of the planet
     * @return Position
     */
    public Vector3d getPosition() {
        return position;
    }

    /**
     * Gets Velocity of the planet
     * @return Velocity
     */
    public Vector3d getVelocity() {
        return velocity;
    }

    /**
     * Sets Mass of the planet
     * @param m Mass
     */
    public void setM(double m) {
        this.m = m;
    }

    /**
     * Sets Position of the planet
     * @param position Position
     */
    public void setPosition(Vector3d position) {
        this.position = position;
    }

    /**
     * Sets Velocity of the planet
     * @param velocity Velocity
     */
    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }

    /**
     * Clone the object
     * @return new Object of the Planet Body
     */
    @Override
    public PlanetBody clone(){
        return new PlanetBody(m, position, velocity);
    }

}
