package WindModel;

import Body.Vector.Vector2d;
import java.awt.*;
import java.util.Random;


public class LandingModule extends Object{

    Vector2d gravityTitan = new Vector2d(0,-0.1325);

    public LandingModule(Vector2d position, Vector2d velocity, double fuel, Vector2d rotation, Vector2d rotationVelocity, ID id){
        super(position, velocity, fuel, rotation, rotationVelocity, id);
        setMass(6000);
        this.rotationVelocity = new Vector2d(1,0);
    }

    public void tick() {

        //velocity = velocity.add(positionWind(position));
        //rotation = rotation.add(rotationVelocity);

        // updating the position, I think this is Euler Solver (not sure to be honest)
        position = position.add(velocity);
        // Velocity influenced by wind
        velocity = velocity.add(generateRandomWind());
        // Velocity influenced by gravity
        velocity = velocity.add(gravityTitan);

        // Makes sure the rocket can not clip through the ground
        position.setX(WindModel.clamp((int)position.getX(),0,WindModel.WIDTH-32));
        position.setY(WindModel.clamp((int)position.getY(),0,WindModel.HEIGHT-32));


        if(position.getY() <= 0) {
            hitTheGround();
        }
        //printPosition();

    }

    // scaled down by 10^-1
    private int maxDeviationWind = 12;
    private int minDeviationWind = 8;

    public Vector2d generateRandomWind(){

        int random_int = (int)Math.floor(Math.random()*(maxDeviationWind-minDeviationWind+1)+minDeviationWind);
        double randomDeviation = random_int * 0.01;
        //System.out.println(randomDeviation);
        // It has to be longer at one value before fluctuating again - HOW ?

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

    public Rectangle getBounds() {
        return new Rectangle((int)position.getX(),(int)position.getY(),12,24);
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
