package WindModel;

import Body.Vector.Vector2d;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class LandingModule extends Object{

    public LandingModule(Vector2d position, Vector2d velocity, double fuel, Vector2d rotation, Vector2d rotationVelocity){
        super(position, velocity, fuel, rotation, rotationVelocity);
        setMass(6000);
        this.velocity = new Vector2d(0,1);
        this.rotationVelocity = new Vector2d(1,0);
    }

    public void tick() {
        position = position.add(velocity);
        rotation = rotation.add(rotationVelocity);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect((int)position.getX(),(int)position.getY(),12,24);
    }

}
