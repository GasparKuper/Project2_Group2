package WindModel;

import Body.Vector.Vector2d;
import java.awt.*;


public class LandingModule extends Object{

    Vector2d gravityTitan = new Vector2d(0,-1.352);

    public LandingModule(Vector2d position, Vector2d velocity, double fuel, double rotation, double rotationVelocity, ID id){
        super(position, velocity, fuel, rotation, rotationVelocity, id);
        setMass(6000);
        this.rotationVelocity = 1;
    }

    public void tick() {

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

        // once the probe is at the ground the velocity is at zero
        if(position.getY() <= 0) {
            stopVelocity();
        }

        // if the probe is at the landing position its done!
        if(position.getY() < 10 && position.getX() <= 240 && position.getX() >= 210 ){
            System.out.println("Success!!!!");}

        //printPosition();

    }


    //TODO We launch the lander at the distance approximately 200.000 meters from Titan
    //todo Do wind force in meter per seconds
    //todo Scale the data only for representation in the GUI (No kilometers in the calculation)
    public Vector2d generateRandomWind(){

        int MAX_DEVIATION_WIND = 12;
        int MIN_DEVIATION_WIND = 8;

        // creating a random number between the max and min Deviation constants
        int random_int = (int)Math.floor(Math.random()*(MAX_DEVIATION_WIND - MIN_DEVIATION_WIND +1)+ MIN_DEVIATION_WIND);

        double randomDeviation = random_int * 0.01;

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

    public  void stopVelocity(){
        velocity.setY(0);
        velocity.setX(0);
    }

    public void printPosition(){
        System.out.println("Position of lander X: " + position.getX());
        System.out.println("Position of lander Y: " + position.getY());
        System.out.println("Velocity of lander X: " + velocity.getX());
        System.out.println("Velocity of lander Y: " + velocity.getY());
    }




}
