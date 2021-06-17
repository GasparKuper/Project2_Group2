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

    private double SCALE = 1000;


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
        this.fixedPoint = new Point2D.Double(position.getX(), position.getY()/SCALE);

        movingPoint = rotateLineClockWise(fixedPoint, movingPoint, -1);
    }

    @Override
    public void render(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        this.fixedPoint = new Point2D.Double(position.getX() , position.getY()/SCALE);
        Line2D line = new Line2D.Double(fixedPoint.getX(),fixedPoint.getY(),movingPoint.getX(),movingPoint.getY());
        g2d.draw(line);

    }

    public Point2D.Double rotateLineClockWise(Point2D fixedPoint,Point2D movingPoint, int angle) {
        double xRot =  fixedPoint.getX() + Math.cos(Math.toRadians(angle)) * (movingPoint.getX() - fixedPoint.getX()) - Math.sin(Math.toRadians(angle)) * (movingPoint.getY() - fixedPoint.getY());
        double yRot =  fixedPoint.getY() + Math.sin(Math.toRadians(angle)) * (movingPoint.getX() - fixedPoint.getX()) + Math.cos(Math.toRadians(angle)) * (movingPoint.getY() - fixedPoint.getY());
        return new Point2D.Double(xRot, yRot);

    }
}
