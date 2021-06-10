package StochasticWindModel;

import Body.Vector3d;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MoverObject {

    int mass;
    Vector3d position;
    Vector3d velocity;
    Vector3d acceleration;

    public  MoverObject(){
        this.mass = 100;
        this.position = new Vector3d(200,0,0);
        this.velocity = new Vector3d(0,0,0);
        this.acceleration = new Vector3d(0,0,0);
    }

    public void applyForce(Vector3d force){
        this.acceleration.add(force);
    }

    public void update() {

        this.velocity.add(this.acceleration);
        this.acceleration.add(this.velocity);
        this.acceleration.setX(0);
        this.acceleration.setY(0);
        this.acceleration.setZ(0);
    }

    public Shape show() {
        Shape mover = new RoundRectangle2D.Double(this.position.getX(),this.position.getY(), 20,40,3,3);
        return mover;

    }



}
