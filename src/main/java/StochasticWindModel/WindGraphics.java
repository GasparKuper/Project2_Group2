package StochasticWindModel;

import Body.Vector3d;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;

/* This class will paint the vectors in the GUI
 *
 */


public class WindGraphics extends JPanel {

    private Vector3d vector;

    public WindGraphics(Vector3d vector){
        this.vector = vector;
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        Graphics2D g2D = (Graphics2D) g; // Casting the object g to 2d graphics
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.setColor(Color.WHITE);

        // the times 10 should be the magnitude of the vector!
        Shape vectorLine = new Line2D.Double(vector.getX(), vector.getY(), vector.getX()*10, vector.getY()*10);
        g2D.setStroke(new BasicStroke(2));
        g2D.draw(vectorLine);
    }


}
