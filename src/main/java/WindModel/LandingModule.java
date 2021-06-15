package WindModel;

import Body.Vector.Vector2d;
import java.awt.*;
import java.util.Random;

public class LandingModule extends Object{

    public LandingModule(Vector2d position, Vector2d velocity, double fuel, Vector2d rotation, Vector2d rotationVelocity, ID id){
        super(position, velocity, fuel, rotation, rotationVelocity, id);
        setMass(6000);
        this.rotationVelocity = new Vector2d(1,0);
    }

    public void tick() {
        velocity = positionWind(position);
        position = position.add(velocity);
        rotation = rotation.add(rotationVelocity);
        System.out.println("Position of lander y: " + position.getY());
        if(position.getY() < 10){
            position.setY(0);
            velocity.setX(0);
            position.setX(position.getX());
            System.out.println("Success!!!!");
        }
    }

    public Vector2d positionWind (Vector2d position){
        // Trying to implement the gradient function of the strength of the wind
        double yScalar = 0.004 * position.getY() * position.getY() + 0.173 * position.getY();
        System.out.println("Position of lander x: " + position.getX());
        yScalar = 0.0000001*yScalar;
        System.out.println("yScalar: " + yScalar);
        System.out.println("velocityX: " + velocity.getX());
        velocity.setX(velocity.getX()+yScalar);
        return velocity;
    }



    public void render(Graphics2D g) {
        // rotation the coordinate system, so that the origin is at the bottom
        g.drawLine(10,10,10,600);
        g.translate(0, 500.0);
        g.scale(-1.0, 1.0);
        g.rotate(Math.PI/1);

        g.setColor(Color.WHITE);
        g.fillRect((int)position.getX(),(int)position.getY(),12,24);

    }



}
