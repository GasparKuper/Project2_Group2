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
    MoverObject movingObject;

    public WindVectorCalculations(double x, double y){

        this.vector = new Vector3d(x,y,0); // initialize Wind vector

        this.movingObject = new MoverObject(); // initialize the moving object
    }


    // if I need more than one Vector
    public ArrayList<Vector3d> createVectorGrid(){
        ArrayList<Vector3d> windVectors = new ArrayList<>();
        return windVectors;
    }

    // Newtons Second Law
    //public void applyForce(force) {
    //    this.acc = force;
    //}


    public void updateVector(){
        this.vector = addRandomNumber(vector);
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
