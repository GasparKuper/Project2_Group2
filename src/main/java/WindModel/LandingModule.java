package WindModel;

import Body.Vector.Vector2d;
import java.awt.*;


public class LandingModule extends Object{

    Vector2d gravityTitan = new Vector2d(0,-1.325);

    public LandingModule(Vector2d position, Vector2d velocity, double fuel, Vector2d rotation, Vector2d rotationVelocity, ID id){
        super(position, velocity, fuel, rotation, rotationVelocity, id);
        setMass(6000);
        this.rotationVelocity = new Vector2d(1,0);
    }

    // returns a rectangle object
    public Rectangle getBounds() {
        return new Rectangle((int)position.getX(),(int)position.getY(),12,24);
    }

    public void tick() {

        //velocity = positionWind(position);
        position = position.add(velocity);
        rotation = rotation.add(rotationVelocity);

        velocity = velocity.add(gravityTitan);


        // Makes sure the rocket can not clip through the ground
        position.setX(WindModel.clamp((int)position.getX(),0,WindModel.WIDTH-32));
        position.setY(WindModel.clamp((int)position.getY(),0,WindModel.HEIGHT-32));


        if(position.getY() <= 0) {
            hitTheGround();
        }

        printPosition();

    }

    public Vector2d positionWind (Vector2d position){
        // Trying to implement the gradient function of the strength of the wind

        double yScalar = 0.004 * position.getY() * position.getY() + 0.173 * position.getY();
        System.out.println("Position of lander x: " + position.getX());
        yScalar = 0.00000001*yScalar;
        //System.out.println("yScalar: " + yScalar);
        //System.out.println("velocityX: " + velocity.getX());
        velocity.setX(velocity.getX()+yScalar);
        return velocity;
    }


    public void render(Graphics2D g) {
        // rotation the coordinate system, so that the origin is at the bottom
        g.drawLine(10,10,10,600);
        g.translate(0, 500.0);
        g.scale(-1.0, 1.0);
        g.rotate(Math.PI/1);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.draw(getBounds());

        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)position.getX(),(int)position.getY(),12,24);

        // drawing the ground of Titan
        g.setColor(Color.RED);
        Stroke stroke = new BasicStroke(2f);
        g.setStroke(stroke);
        g.drawLine(10, 0, 800, 0);

    }

    public  void hitTheGround(){
        velocity.setY(0);
        velocity.setX(0);
    }

    public void printPosition(){
        System.out.println("Position of lander X: " + position.getX());
        System.out.println("Position of lander Y: " + position.getY());
        System.out.println("Velocity of lander X: " + velocity.getX());
        System.out.println("Velocity of lander Y: " + velocity.getY());

        if(position.getY() < 10 && position.getX() < 10){
            System.out.println("Success!!!!");
        }
    }



}
