package StochasticWindModel;

import javax.swing.*;
import java.awt.*;

public class Renderer {

    private JFrame frame;
    public Renderer(JFrame frame){
        this.frame = frame;
    }
    public void render(){

        Graphics g = frame.getGraphics();

        g.setColor(Color.GREEN);
        g.drawOval(100,100,600,400);


        g.dispose();
    }
}
