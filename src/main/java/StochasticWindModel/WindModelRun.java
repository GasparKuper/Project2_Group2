package StochasticWindModel;

import Body.Vector3d;

import java.util.*;

public class WindModelRun {

    public static void main (String[] args){

        // a method to create a grid of vectors

        WindVectorCalculations vector = new WindVectorCalculations(2,3);

        // if I need more than one Vector
        ArrayList<Vector3d> windVectors = new ArrayList<>();

        vector.updateVector();
        vector.draw();

    }
}
