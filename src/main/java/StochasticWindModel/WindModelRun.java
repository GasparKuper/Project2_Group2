package StochasticWindModel;

import Body.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class WindModelRun implements Runnable{

    public static final String VERSION = "0.0.1";
    public static final String TITLE = "Stochastic Wind Model " + VERSION;
    public static final Dimension SCREEN_SIZE = new Dimension(800,600);

    public JFrame frame;
    public Renderer rend;
    public ForceList forceList;

    public WindModelRun(){
        this.frame = new JFrame();
        frame.setSize(SCREEN_SIZE);
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        this.rend = new Renderer(frame);
        this.forceList = new ForceList();

        frame.setVisible(true);
    }

    public static void main(String[] args){

        WindModelRun main = new WindModelRun();  // create Main
        new Thread(main).start(); // start Main

        // a method to create a grid of vectors
        //WindVectorCalculations WindCalculations = new WindVectorCalculations(5,3);
        // if I need more than one Vector
        //ArrayList<Vector3d> windVectors = new ArrayList<>();
        //WindCalculations.updateVector();
        //WindCalculations.draw();

    }

    @Override
    public void run() {
        while(true){
            rend.render();
            try {Thread.sleep(50); }  // it will render the screen 20 time per second
            catch(InterruptedException ie){}
        }
    }




}
