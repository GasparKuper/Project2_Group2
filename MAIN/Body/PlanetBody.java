package MAIN.Body;

import MAIN.Interfaces.Vector3dInterface;

public class PlanetBody {

    private double m;

    public Vector3d position;

    public Vector3d velocity;

    public PlanetBody(double m, Vector3dInterface position, Vector3dInterface velocity){
        this.m = m;
        this.position = (Vector3d) position;
        this.velocity = (Vector3d) velocity;
    }

    public double getM() {
        return m;
    }

    public Vector3d getPosition() {
        return position;
    }

    public Vector3d getVelocity() {
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
