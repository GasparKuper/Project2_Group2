package StochasticWindModel;

import Body.Vector3d;

import javax.swing.*;
import java.awt.*;

/* This class will is creating the Frame/Canvas for the Vectors
 *
 */

public class WindFrame extends JFrame {

    WindGraphics windGraphics;

    public WindFrame(Vector3d vector){
        this.setSize(420,720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.windGraphics = new WindGraphics(vector);
        this.add(windGraphics);
        this.setVisible(true);
    }

}
