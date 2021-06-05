package StochasticWindModel;

import Body.Vector3d;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/* This class will manipulate the WindVectors
 *
 */

public class WindVectorCalculations extends JFrame {

    private Vector3d vector;

    public WindVectorCalculations(double x, double y){
        this.vector = new Vector3d(x,y,0);
    }


    // if I need more than one Vector
    public ArrayList<Vector3d> createVectorGrid(){
        ArrayList<Vector3d> windVectors = new ArrayList<>();
        return windVectors;
    }


    public void updateVector(){

        System.out.println("Before manipulation: " + this.vector.toString());
        // adding a random Number to the vector
        this.vector = addRandomNumber(vector);
        System.out.println("After manipulation: " + this.vector.toString());
    }

    public void draw(){
        WindFrame windFrame = new WindFrame(this.vector);
    }



    public Vector3d addRandomNumber(Vector3d vector){
        double x = vector.getX() + randomNumber();
        vector.setX(x);
        double y = vector.getY() + randomNumber();
        vector.setY(y);
        return vector;
    }

    public double randomNumber(){
        double randomNumber = ThreadLocalRandom.current().nextDouble(0, 3 + 1);
        return randomNumber;
    }

}
