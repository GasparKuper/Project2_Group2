package Titan.Body;

import Titan.Interfaces.Vector3dInterface;

public class PlanetBody {

    private double m;

    private Vector3dInterface position;

    private Vector3dInterface velocity;

    public PlanetBody(double m, Vector3dInterface position, Vector3dInterface velocity){
        this.m = m;
        this.position = position;
        this.velocity = velocity;
    }

    public double getM() {
        return m;
    }

    public Vector3dInterface getPosition() {
        return position;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setM(double m) {
        this.m = m;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }

}
