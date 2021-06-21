package WindModel;

import Body.Vector.Vector2d;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


public class WindFlag extends Object{

    private int rotationAngle;
    private Point2D movingPoint;
    private Point2D fixedPoint;
    private double angle = 90;
    Vector2d gravityTitan = new Vector2d(0,-1.352);



    public WindFlag(Vector2d position) {
        super(position);
        this.velocity = new Vector2d(0,0);
        this.rotationVelocity = 0;
        this.rotation = 0;
        this.movingPoint = new Point2D.Double(position.getX(),position.getY()+20);
        this.id = ID.WindFlag;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void tick() {

        rotationVelocity = rotationVelocity + generateRandomWind();
        angle = angle + rotationVelocity;

        //System.out.println("angle " + angle);

        movingPoint = rotateLine(fixedPoint);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        this.fixedPoint = new Point2D.Double(position.getX() , position.getY());
        Line2D line = new Line2D.Double(fixedPoint.getX(),fixedPoint.getY(),movingPoint.getX(),movingPoint.getY());
        g2d.draw(line);
    }

    public double generateRandomWind(){
        int MAX_DEVIATION_WIND = 90;
        int MIN_DEVIATION_WIND = -90;

        // creating a random number between the max and min Deviation constants
        int random_int = (int)Math.floor(Math.random()*(MAX_DEVIATION_WIND - MIN_DEVIATION_WIND +1)+ MIN_DEVIATION_WIND);

        double randomDeviation = random_int;

        // once the lander is below 300 km the wind changes the direction ! Wow !
        if (this.position.getY() < 300){
            randomDeviation = randomDeviation * -1;
        }

        // implementing the function of wind strength according to height
        double yScalar = 0.004 * position.getY() * position.getY() + 0.173 * position.getY();
        yScalar = yScalar * 0.0001;
        // adding the yScalar to the randomDeviation
        randomDeviation = randomDeviation + (randomDeviation * yScalar);
        //System.out.println("randomDeviation " + randomDeviation);

        Vector2d randomWindVector = new Vector2d(randomDeviation, 0);
        return randomDeviation;
    }


    public Point2D.Double rotateLine(Point2D fixedPoint) {

        double xRot = fixedPoint.getX() + Math.cos(Math.toRadians(angle)) * 20;
        double yRot = fixedPoint.getY() + Math.sin(Math.toRadians(angle)) * 20;

        return new Point2D.Double(xRot, yRot);
    }
}
