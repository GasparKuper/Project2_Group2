package MAIN.GeneticAlgorithm;

import MAIN.Interfaces.StateInterface;

public class Individual {

    private StateInterface position;

    private double time, latitude, longtitude;

    public Individual(StateInterface position , double time, double longitude, double latitude){
        this.position = position;
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longitude;
    }

    public double getTime() {
        return time;
    }
}
